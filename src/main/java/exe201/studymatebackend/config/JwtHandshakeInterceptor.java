package exe201.studymatebackend.config;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtHandshakeInterceptor implements ChannelInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(JwtHandshakeInterceptor.class);

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        String command = accessor.getCommand() != null ? accessor.getCommand().name() : null;

        logger.info("WS preSend command={}", command);

        // Xử lý tất cả các command
        if (command != null) {
            String authHeader = extractAuthHeader(message, accessor);

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                try {
                    String username = jwtUtil.getUsernameFromToken(token);
                    logger.info("WS token parsed, username={}", username);

                    if (username == null) {
                        logger.warn("WS token parsed but username is null");
                        throw new BadCredentialsException("Invalid token: username missing");
                    }

                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                    if (jwtUtil.validateToken(token, userDetails)) {
                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(
                                        userDetails, null, userDetails.getAuthorities());

                        // Set vào SecurityContext
                        SecurityContextHolder.getContext().setAuthentication(authentication);

                        // Set vào accessor
                        accessor.setUser(authentication);

                        // Lưu token vào headers
                        accessor.setHeader("JWT_TOKEN", token);
                        accessor.setHeader("Authorization", authHeader);

                        logger.info("WS authentication set for user={}", username);
                    } else {
                        logger.warn("WS token validation failed for username={}", username);
                        throw new BadCredentialsException("Invalid token");
                    }
                } catch (Exception ex) {
                    logger.error("WS auth error: {}", ex.getMessage(), ex);
                    throw ex;
                }
            } else {
                logger.warn("No valid Authorization header found for command: {}", command);
            }
        }

        return message;
    }

    private String extractAuthHeader(Message<?> message, StompHeaderAccessor accessor) {
        String authHeader = null;

        // 1) Try common accessor helper
        try {
            authHeader = accessor.getFirstNativeHeader("Authorization");
        } catch (Exception ignored) {
        }

        // 2) Try lowercase variant
        if (authHeader == null) {
            try {
                authHeader = accessor.getFirstNativeHeader("authorization");
            } catch (Exception ignored) {
            }
        }

        // 3) Try to read raw nativeHeaders from message headers
        if (authHeader == null) {
            Object nativeHeadersObj = message.getHeaders().get("nativeHeaders");
            if (nativeHeadersObj instanceof java.util.Map) {
                java.util.Map<?, ?> nativeHeaders = (java.util.Map<?, ?>) nativeHeadersObj;
                for (Object keyObj : nativeHeaders.keySet()) {
                    String key = keyObj == null ? "" : keyObj.toString();
                    if ("authorization".equalsIgnoreCase(key)) {
                        Object vals = nativeHeaders.get(keyObj);
                        if (vals instanceof java.util.List) {
                            java.util.List<?> list = (java.util.List<?>) vals;
                            if (!list.isEmpty()) {
                                authHeader = list.get(0) == null ? null : list.get(0).toString();
                                break;
                            }
                        } else if (vals != null) {
                            authHeader = vals.toString();
                            break;
                        }
                    }
                }
            }
        }

        logger.debug("Extracted auth header: {}", authHeader != null ? "Bearer ***" : "null");
        return authHeader;
    }
}
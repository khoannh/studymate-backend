package exe201.studymatebackend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    private JwtHandshakeInterceptor jwtHandshakeInterceptor;

    // ✅ Khai báo endpoint cho client connect
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns(
                        "https://studymate.khoannh.id.vn",  // Domain chính
                        "http://localhost:5173",            // Localhost
                        "https://studymate-frontend-murex.vercel.app", // Domain hiện tại đang bị lỗi
                        "https://*.vercel.app"              // <--- QUAN TRỌNG: Chấp nhận mọi sub-domain của Vercel
                )
                .withSockJS();
    }

    // ✅ Định nghĩa 2 "đường đi"
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // đường client gửi lên
        registry.setApplicationDestinationPrefixes("/app");

        // đường server gửi ra (realtime)
        registry.enableSimpleBroker("/topic");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(jwtHandshakeInterceptor);
    }

    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        registration.interceptors(jwtHandshakeInterceptor);
    }
}
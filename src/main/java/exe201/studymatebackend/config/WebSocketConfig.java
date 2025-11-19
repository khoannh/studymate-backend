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
        registry.addEndpoint("/ws") // client sẽ connect đến /ws
                .setAllowedOriginPatterns("https://studymate.khoannh.id.vn", "http://localhost:5173") // cho phép mọi domain (dev mode)
                .withSockJS(); // fallback nếu browser không hỗ trợ websocket
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
    package com.leoholmer;

    import com.leoholmer.AllMusic.backend.util.PasswordEncoder;
    import org.springframework.context.annotation.Configuration;

    @Configuration
    public class Application{
        public PasswordEncoder passwordEncoder() {
            return new PasswordEncoder();
        }
    }
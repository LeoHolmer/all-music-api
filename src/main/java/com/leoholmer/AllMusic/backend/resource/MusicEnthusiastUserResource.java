package com.leoholmer.AllMusic.backend.resource;

import com.leoholmer.AllMusic.backend.dto.AuthenticationRequestDTO;
import com.leoholmer.AllMusic.backend.model.MusicEnthusiastUser;
import com.leoholmer.AllMusic.backend.service.AuthenticationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enthusiast")
public class MusicEnthusiastUserResource {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(value = "/auth", produces = "application/json")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequestDTO dto) {
        try {
            MusicEnthusiastUser user = modelMapper.map(dto, MusicEnthusiastUser.class);
            String token = authenticationService.authenticate(user);
            return ResponseEntity.ok().body("{\"token\": \"" + token + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }
}

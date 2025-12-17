package com.leoholmer.AllMusic.backend.resource;

import com.leoholmer.AllMusic.backend.dto.AuthenticationRequestDTO;
import com.leoholmer.AllMusic.backend.model.MusicEnthusiastUser;
import com.leoholmer.AllMusic.backend.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enthusiast")
@Tag(name = "Enthusiast Authentication", description = "API para autenticación de entusiastas musicales")
public class MusicEnthusiastUserResource {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(value = "/auth", produces = "application/json")
    @Operation(summary = "Autenticar entusiasta musical", description = "Autentica a un entusiasta musical y devuelve un token JWT")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Autenticación exitosa",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(example = "{\"token\": \"jwt-token-here\"}"))),
        @ApiResponse(responseCode = "401", description = "Credenciales inválidas",
                content = @Content(mediaType = "text/plain"))
    })
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

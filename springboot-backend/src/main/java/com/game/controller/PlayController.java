package com.game.controller;

import com.game.model.CalculationDto;
import com.game.model.PlaySessionDTO;
import com.game.model.SessionRequest;
import com.game.model.StepCalculationRequest;
import com.game.service.PlayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/game")
public class PlayController {

	private final PlayService playService;

	@Autowired
	public PlayController(PlayService playService) {
		this.playService = playService;
	}

	@GetMapping("/getSession/{sessionId}")
	public ResponseEntity<PlaySessionDTO> getSession(@PathVariable("sessionId") String sessionId) {
		PlaySessionDTO playSessionDTO = playService.createSession(sessionId);
		return ResponseEntity.ok(playSessionDTO) ;
	}

	@PostMapping("/calculateStep/{sessionId}")
	public ResponseEntity<CalculationDto> calculateStep(@RequestBody StepCalculationRequest stepCalculationRequest, @PathVariable("sessionId") String sessionId) {
		CalculationDto calculationDto = playService.calculateStep(sessionId,stepCalculationRequest);
		return ResponseEntity.ok(calculationDto) ;
	}
}

package io.zipcoder.tc_spring_poll_application.controllers;

import io.zipcoder.tc_spring_poll_application.domain.Vote;
import io.zipcoder.tc_spring_poll_application.repositories.VoteRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.inject.Inject;

@Controller
public class VoteController {

    @Inject
    private VoteRepository voteRepository;

    @PostMapping("/polls/{pollId}/votes")
    public ResponseEntity<?> createVote(@PathVariable Long pollId, @RequestBody Vote vote) {
        vote = voteRepository.save(vote);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(vote.getId()).toUri());
        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @GetMapping("/polls/votes")
    public ResponseEntity<Iterable<Vote>> getAllVotes() {
        Iterable<Vote> response = voteRepository.findAll();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/polls/{pollId}/votes")
    public Iterable<Vote> getVote(@PathVariable Long pollId) {
        return voteRepository.findVotesByPoll(pollId);
    }

    @GetMapping("/polls/{pollId}/vote")
    public ResponseEntity<Vote> getVote1(@PathVariable Long pollId) {
        Vote response = voteRepository.findOne(pollId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}

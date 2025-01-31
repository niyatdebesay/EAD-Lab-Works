package com.example.Security.Salon;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class Greeting {
    @GetMapping("/")
    public ResponseEntity<String> SayHello(){
        return ResponseEntity.ok("Hello World");

    }

    @GetMapping("/bye")
    public ResponseEntity<String> SayGoodBye(){
        return ResponseEntity.ok("Bye World");

    }

    @GetMapping("/csrf-token")
    public CsrfToken getCsrfToken(HttpServletRequest request){
        return (CsrfToken) request.getAttribute("_csrf");


    }
    @PostMapping("/sayHi")
    public ResponseEntity<String> SayHi(@RequestBody Object name){
        return ResponseEntity.ok("Hi " + name);
    }
}

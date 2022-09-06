package com.example.restservice;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class GreetingController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}

	@Autowired
	private CounterRepository counterRepository;

	@GetMapping("/persistent_greeting")
	public Greeting persistentGreeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		Counter persistentCounter = counterRepository.findById(1L).orElseGet(() -> new Counter(0L));
		Long persistentCounterValue = persistentCounter.incrementAndGet();
		counterRepository.save(persistentCounter);
		return new Greeting(persistentCounterValue, String.format(template, name));
	}
}

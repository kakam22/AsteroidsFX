package dk.sdu.csbe.ScoreService;

import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/score")
public class ScoreController {

    private final AtomicInteger score = new AtomicInteger();

    @GetMapping
    public int getScore() {
        return score.get();
    }

    @PostMapping("/add")
    public void addScore(@RequestParam int points) {
        score.addAndGet(points);
    }

    @PostMapping("/reset")
    public void reset() {
        score.set(0);
    }
}

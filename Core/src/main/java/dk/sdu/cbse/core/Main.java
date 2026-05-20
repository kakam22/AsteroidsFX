package dk.sdu.cbse.core;

import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main extends Application {

    private AnnotationConfigApplicationContext context;

    public static void main(String[] args) {
        launch(Main.class, args);
    }

    @Override
    public void init() {
        context = new AnnotationConfigApplicationContext(AppConfig.class);
    }

    @Override
    public void start(Stage stage) {
        context.getBean(Game.class).start(stage);
    }

    @Override
    public void stop() {
        if (context != null) {
            context.getBean(Game.class).stop();
            context.close();
        }
    }
}

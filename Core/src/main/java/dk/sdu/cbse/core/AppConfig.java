package dk.sdu.cbse.core;

import dk.sdu.cbse.data.GameData;
import dk.sdu.cbse.data.World;
import dk.sdu.cbse.services.IEntityProcessingService;
import dk.sdu.cbse.services.IGamePluginService;
import dk.sdu.cbse.services.IPostEntityProcessingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration(proxyBeanMethods = false)
public class AppConfig {

    @Bean
    public GameData gameData() {
        return new GameData();
    }

    @Bean
    public World world() {
        return new World();
    }

    @Bean
    public List<IGamePluginService> gamePluginServices() {
        return new ArrayList<>(ServiceLocator.INSTANCE.locateAll(IGamePluginService.class));
    }

    @Bean
    public List<IEntityProcessingService> entityProcessingServices() {
        return new ArrayList<>(ServiceLocator.INSTANCE.locateAll(IEntityProcessingService.class));
    }

    @Bean
    public List<IPostEntityProcessingService> postEntityProcessingServices() {
        return new ArrayList<>(ServiceLocator.INSTANCE.locateAll(IPostEntityProcessingService.class));
    }

    @Bean
    public ScoreClient scoreClient() {
        return new ScoreClient();
    }

    @Bean
    public Game game(
            GameData gameData,
            World world,
            ScoreClient scoreClient,
            List<IGamePluginService> gamePluginServices,
            List<IEntityProcessingService> entityProcessingServices,
            List<IPostEntityProcessingService> postEntityProcessingServices
    ) {
        return new Game(
                gameData,
                world,
                scoreClient,
                gamePluginServices,
                entityProcessingServices,
                postEntityProcessingServices
        );
    }
}

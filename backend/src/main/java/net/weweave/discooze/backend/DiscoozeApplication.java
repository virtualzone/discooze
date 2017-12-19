package net.weweave.discooze.backend;

import net.weweave.discooze.backend.domain.*;
import net.weweave.discooze.backend.service.CommentRepository;
import net.weweave.discooze.backend.service.PanelRepository;
import net.weweave.discooze.backend.service.SystemSettingRepository;
import net.weweave.discooze.backend.service.UserRepository;
import net.weweave.discooze.backend.util.AutowireHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DiscoozeApplication {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(DiscoozeApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void checkDefaultSettings(ApplicationReadyEvent event) {
        System.out.println("Checking default settings...");
        UserRepository userRepository = event.getApplicationContext().getBean(UserRepository.class);
        PanelRepository panelRepository = event.getApplicationContext().getBean(PanelRepository.class);
        SystemSettingRepository systemSettingRepository = event.getApplicationContext().getBean(SystemSettingRepository.class);
        CommentRepository commentRepository = event.getApplicationContext().getBean(CommentRepository.class);
        User adminUser = userRepository.firstAdmin();
        if (adminUser == null) {
            adminUser = this.createAdminUser(userRepository);
            System.out.println("Created admin user.");
        }
        if (panelRepository.count() == 0) {
            Panel panel = this.createDefaultPanel(panelRepository);
            this.setHomePanel(systemSettingRepository, panel);
            this.createComment(commentRepository, panel, adminUser);
            System.out.println("Created default panel and made it the home panel.");
        }
    }

    private Panel createDefaultPanel(PanelRepository panelRepository) {
        Panel panel = new Panel();
        panel.setTitle("Default");
        panel.setUrl("/");
        panel.setType(PanelType.TEXT);
        panel.setHeadline("Welcome to Discooze");
        panel.setContent("Your Discooze installation is up and running.\n" +
                "You can go to the admin panel at /_admin/ now to set up your panels.");
        panel.setFooter("&copy; 2017 weweave.");
        panel = panelRepository.save(panel);
        return panel;
    }

    private void setHomePanel(SystemSettingRepository systemSettingRepository, Panel panel) {
        SystemSetting homePanelSetting = new SystemSetting(SystemSettingKey.HOME_PANEL);
        homePanelSetting.setValue(panel.getId().toString());
        systemSettingRepository.save(homePanelSetting);
    }

    private User createAdminUser(UserRepository userRepository) {
        User user = new User();
        user.setUsername("admin");
        user.setDisplayName("System Administrator");
        user.setLoginType(UserLoginType.PASSWORD);
        user.setPlainPassword("admin");
        user.setRoleAdmin(true);
        user = userRepository.save(user);
        return user;
    }

    private void createComment(CommentRepository commentRepository, Panel panel, User user) {
        Comment comment = new Comment();
        comment.setAuthor(user);
        comment.setPanel(panel);
        comment.setText("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor " +
                "invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo " +
                "duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor " +
                "sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor " +
                "invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et " +
                "justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem " +
                "ipsum dolor sit amet.");
        commentRepository.save(comment);
    }

    @Bean
    public AutowireHelper autowireHelper(){
        return AutowireHelper.getInstance();
    }
}

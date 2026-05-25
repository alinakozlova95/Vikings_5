/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package mephi.viking5;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import mephi.viking5.gui.VikingDesktopFrame;
import mephi.viking5.controller.VikingListener;
import mephi.viking5.service.VikingService;
import mephi.viking5.service.VikingStatisticsService;
/**
 *
 * @author alina
 */


import javax.swing.SwingUtilities;

@SpringBootApplication
public class Viking5 {

    public static void main(String[] args) {
        System.out.println(java.awt.GraphicsEnvironment.isHeadless());

        SpringApplication app = new SpringApplication(Viking5.class);
        app.setHeadless(false);
        app.setWebApplicationType(org.springframework.boot.WebApplicationType.SERVLET);

        ConfigurableApplicationContext context = app.run(args);

        VikingService vikingService = context.getBean(VikingService.class);
        VikingStatisticsService statsService = context.getBean(VikingStatisticsService.class);
        VikingListener vikingListener = context.getBean(VikingListener.class);

        vikingService.setVikingListener(vikingListener);

        SwingUtilities.invokeLater(() -> {
            VikingDesktopFrame frame = new VikingDesktopFrame(vikingService, statsService);
            vikingListener.setGui(frame);
            frame.setVisible(true);});
    }
}
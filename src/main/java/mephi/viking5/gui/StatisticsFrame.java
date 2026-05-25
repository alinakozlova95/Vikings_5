package mephi.viking5.gui;

import mephi.viking5.model.BeardStyle;
import mephi.viking5.model.HairColor;
import mephi.viking5.service.VikingStatisticsService;

import javax.swing.*;
import java.awt.*;

public class StatisticsFrame extends JFrame {

    private final VikingStatisticsService statsService;
    private final JTextArea outputArea;

    public StatisticsFrame(VikingStatisticsService statsService) {
        this.statsService = statsService;

        setTitle("Статистика викингов");
        setSize(750, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        JPanel agePanel = createGroup("Возраст");

        addBtn(agePanel, "Старше 30", () ->
                print("Старше 30", statsService.countAgeGreaterThan(30)));

        addBtn(agePanel, "Младше 20", () ->
                print("Младше 20", statsService.countAgeLessThan(20)));

        addBtn(agePanel, "25-35", () ->
                print("25-35", statsService.countAgeBetween(25, 35)));

        panel.add(agePanel);

        JPanel appearance = createGroup("Внешность");

        addBtn(appearance, "Long + Blond", () ->
                print("Long + Blond",
                        statsService.countBeardAndHair(BeardStyle.LONG, HairColor.Blond)));

        addBtn(appearance, "Рыжебородые", this::showRedBearded);

        panel.add(appearance);

        JPanel equip = createGroup("Снаряжение");

        addBtn(equip, "1-2 топора", () ->
                print("1-2 топора", statsService.countOneOrTwoAxes()));

        addBtn(equip, "Легендарные", this::showLegendary);

        panel.add(equip);

        JPanel random = createGroup("Случайное");

        addBtn(random, "Рост > 180", this::showRandomTall);

        panel.add(random);

        JPanel ids = createGroup("ID");

        addBtn(ids, "Max ID", () ->
                outputArea.append("Max ID: " + statsService.maxId() + "\n"));

        addBtn(ids, "Чётные ID", this::showEvenIds);

        panel.add(ids);

        add(panel, BorderLayout.NORTH);
    }
    
    private JPanel createGroup(String title) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder(title));
        return panel;
    }

    private void addBtn(JPanel panel, String text, Runnable action) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(200, 30));
        btn.addActionListener(e -> action.run());

        panel.add(btn);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
    }

    private void print(String label, long value) {
        outputArea.append(label + ": " + value + "\n");
    }


    private void showLegendary() {
        var list = statsService.legendaryEquipment();

        outputArea.append("Легендарное снаряжение: " + list.size() + "\n");

        list.forEach(v ->
                outputArea.append("  " + v.name() + "\n"));
    }

    private void showRedBearded() {
        var list = statsService.redBeardedSortedByAge();

        outputArea.append("Рыжебородые (по возрасту):\n");

        list.forEach(v ->
                outputArea.append("  " + v.name() + " (" + v.age() + ")\n"));
    }

    private void showRandomTall() {
        statsService.randomTallViking()
                .ifPresentOrElse(
                        v -> outputArea.append("Высокий викинг: " + v.name() + "\n"),
                        () -> outputArea.append("Нет викингов выше 180\n")
                );
    }

    private void showEvenIds() {
        outputArea.append("Чётные ID: ");

        for (int id : statsService.evenIds()) {
            outputArea.append(id + " ");
        }

        outputArea.append("\n");
    }
}
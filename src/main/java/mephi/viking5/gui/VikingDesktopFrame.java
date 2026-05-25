package mephi.viking5.gui;
import mephi.viking5.model.Viking;
import mephi.viking5.service.VikingService;
import mephi.viking5.service.VikingStatisticsService;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;
import javax.swing.JOptionPane;


public class VikingDesktopFrame extends JFrame {

    private final VikingService vikingService;
    private final VikingTableModel tableModel = new VikingTableModel();
    private final VikingStatisticsService statsService;
    
    public VikingDesktopFrame(VikingService vikingService, VikingStatisticsService statsService) {
        this.vikingService = vikingService;
        this.statsService = statsService;

        setTitle("Viking Demo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(1000, 420));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel header = new JLabel("Viking Demo", SwingConstants.CENTER);
        header.setFont(header.getFont().deriveFont(Font.BOLD, 18f));
        add(header, BorderLayout.NORTH);

        JTable vikingTable = new JTable(tableModel);
        vikingTable.setRowHeight(28);
        add(new JScrollPane(vikingTable), BorderLayout.CENTER);

        JButton createButton = new JButton("Create random viking");
        createButton.addActionListener(event -> onCreateViking());

        JButton massCreateButton = new JButton("Create 10 random vikings");
        massCreateButton.addActionListener(event -> onMassCreateVikings());

        JButton statsButton = new JButton("Statistics");
        statsButton.addActionListener(e -> openStatistics());

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(createButton);
        bottomPanel.add(massCreateButton);
        bottomPanel.add(statsButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private void onCreateViking() {
        vikingService.createRandomViking();
    }
    public void removeViking(int id){
        tableModel.removeViking(id);
    }

    public void updateViking(Viking viking){
        tableModel.updateViking(viking);
    }
    
    public void addNewViking(Viking viking){
        tableModel.addViking(viking);
    }
    
    private void openStatistics() {
        StatisticsFrame frame = new StatisticsFrame(statsService);
        frame.setVisible(true);
    }
    
    private void onMassCreateVikings() {
    List<Viking> newVikings = vikingService.generateMultipleRandom(10);
    for (Viking v : newVikings) {
        tableModel.addViking(v);  
    }
    JOptionPane.showMessageDialog(this, 
        "Added " + newVikings.size() + " vikings!", 
        "Mass generation", 
        JOptionPane.INFORMATION_MESSAGE);
}
}
package view;

import controller.ZoomWorker;
import model.DataModel;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class GUI extends JFrame {
    private JProgressBar progress = new JProgressBar();
    private JTextField tfFolder = new JTextField();
    private JTextField tfLimWidth = new JTextField("3700");
    private JTextField tfLimHeight = new JTextField("2460");
    private JTextField tfLimRatio = new JTextField("200");

    private JButton btnFolder = new JButton("Browse");
    private JButton btnRun = new JButton("Run");

    private DataModel model;

    private JFileChooser jfc = new JFileChooser(".");

    public GUI(DataModel model) {

        this.model = model;

        setTitle("Photo Zoomer - NGUYEN Le Ly Bang");

        initComponents();

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        pack();

        setLocation((int) ((Toolkit.getDefaultToolkit().getScreenSize().getWidth() - getWidth()) / 2),
                (int) ((Toolkit.getDefaultToolkit().getScreenSize().getHeight() - getHeight()) / 2));
        setResizable(false);
        setVisible(true);
    }

    private void initComponents() {
        JLabel lblFolder = new JLabel("Photos folder");
        JLabel lblLimWidth = new JLabel("Limit Width");
        JLabel lblLimHeight = new JLabel("Limit Height");
        JLabel lblLimRatio = new JLabel("Limit Ratio (%)");

        JPanel pnlMain = new JPanel();
        JPanel pnlInput = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel pnlConfig = new JPanel(new FlowLayout(FlowLayout.LEADING));
        JPanel pnlAction = new JPanel(new FlowLayout());


        lblFolder.setPreferredSize(lblLimRatio.getPreferredSize());
        lblLimHeight.setPreferredSize(lblLimRatio.getPreferredSize());
        lblLimWidth.setPreferredSize(lblLimRatio.getPreferredSize());


        tfFolder.setPreferredSize(new Dimension(300, 25));
        tfLimHeight.setPreferredSize(new Dimension(50, 25));
        tfLimWidth.setPreferredSize(new Dimension(50, 25));
        tfLimRatio.setPreferredSize(new Dimension(50, 25));

        pnlMain.setLayout(new BoxLayout(pnlMain, BoxLayout.Y_AXIS));


        pnlMain.add(pnlInput);
        pnlMain.add(pnlConfig);
        pnlMain.add(pnlAction);

        pnlInput.add(lblFolder);
        pnlInput.add(tfFolder);
        pnlInput.add(btnFolder);

        pnlConfig.add(lblLimWidth);
        pnlConfig.add(tfLimWidth);
        pnlConfig.add(lblLimHeight);
        pnlConfig.add(tfLimHeight);
        pnlConfig.add(lblLimRatio);
        pnlConfig.add(tfLimRatio);



        btnRun.setEnabled(true);
        pnlAction.add(btnRun);

        add(pnlMain, BorderLayout.CENTER);
        add(progress, BorderLayout.SOUTH);

        progress.setStringPainted(true);

        btnFolder.addActionListener(e -> {
            jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int choose = jfc.showOpenDialog(this);
            if (choose == jfc.getApproveButtonMnemonic()) {
                tfFolder.setText(jfc.getSelectedFile().getPath());
            }
        });

        btnRun.addActionListener(e -> {
            if (tfFolder.getText().equals(""))
                return;
            model.setWorkingDir(new File(tfFolder.getText()));
            model.setRatio(Integer.parseInt(tfLimRatio.getText()));
            model.setLimWidth(Integer.parseInt(tfLimWidth.getText()));
            model.setLimHeight(Integer.parseInt(tfLimHeight.getText()));

            new ZoomWorker(this).execute();
        });
    }

    public DataModel getModel() {
        return model;
    }

    public JProgressBar getProgress() {
        return progress;
    }
}
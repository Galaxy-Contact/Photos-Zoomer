package view;

import controller.ZoomWorker;
import model.DataModel;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class GUI extends JFrame {
    private JProgressBar progress = new JProgressBar();
    private JTextField tfFolder = new JTextField();
    private JTextField tfExcel = new JTextField();
    private JTextField tfLimWidth = new JTextField("3700");
    private JTextField tfLimHeight = new JTextField("2460");
    private JTextField tfLimRatio = new JTextField("200");
    private JTextField tfColRef = new JTextField("C");
    private JTextField tfColSize = new JTextField("R");
    private JTextField tfColWidth = new JTextField("S");
    private JTextField tfColHeight = new JTextField("T");

    private JButton btnFolder = new JButton("Browse");
    private JButton btnExcel = new JButton("Browse");
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
        JLabel lblExcel = new JLabel("Excel file");
        JLabel lblLimWidth = new JLabel("Limit Width");
        JLabel lblLimHeight = new JLabel("Limit Height");
        JLabel lblLimRatio = new JLabel("Limit Ratio (%)");
        JLabel lblColRefPhoto = new JLabel("Columns: Photo name");
        JLabel lblColSize = new JLabel("Size (MB)");
        JLabel lblColWidth = new JLabel("Width");
        JLabel lblColHeight = new JLabel("Height");

        JPanel pnlMain = new JPanel();

        JPanel pnlFolder = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel pnlExcel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel pnlConfig = new JPanel(new FlowLayout(FlowLayout.LEADING));
        JPanel pnlConfigColumns = new JPanel(new FlowLayout(FlowLayout.LEADING));
        JPanel pnlAction = new JPanel(new FlowLayout());


        lblFolder.setPreferredSize(lblLimRatio.getPreferredSize());
        lblLimHeight.setPreferredSize(lblLimRatio.getPreferredSize());
        lblLimWidth.setPreferredSize(lblLimRatio.getPreferredSize());
        lblExcel.setPreferredSize(lblLimRatio.getPreferredSize());


        tfFolder.setPreferredSize(new Dimension(300, 25));
        tfExcel.setPreferredSize(new Dimension(300, 25));

        tfLimHeight.setPreferredSize(new Dimension(50, 25));
        tfLimWidth.setPreferredSize(new Dimension(50, 25));
        tfLimRatio.setPreferredSize(new Dimension(50, 25));
        tfColRef.setPreferredSize(new Dimension(50, 25));
        tfColSize.setPreferredSize(new Dimension(50, 25));
        tfColWidth.setPreferredSize(new Dimension(50, 25));
        tfColHeight.setPreferredSize(new Dimension(50, 25));

        pnlMain.setLayout(new BoxLayout(pnlMain, BoxLayout.Y_AXIS));


        pnlMain.add(pnlFolder);
        pnlMain.add(pnlConfig);
        pnlMain.add(pnlExcel);
        pnlMain.add(pnlConfigColumns);
        pnlMain.add(pnlAction);

        pnlFolder.add(lblFolder);
        pnlFolder.add(tfFolder);
        pnlFolder.add(btnFolder);

        pnlConfig.add(lblLimWidth);
        pnlConfig.add(tfLimWidth);
        pnlConfig.add(lblLimHeight);
        pnlConfig.add(tfLimHeight);
        pnlConfig.add(lblLimRatio);
        pnlConfig.add(tfLimRatio);

        pnlExcel.add(lblExcel);
        pnlExcel.add(tfExcel);
        pnlExcel.add(btnExcel);

        pnlConfigColumns.add(lblColRefPhoto);
        pnlConfigColumns.add(tfColRef);
        pnlConfigColumns.add(lblColSize);
        pnlConfigColumns.add(tfColSize);
        pnlConfigColumns.add(lblColWidth);
        pnlConfigColumns.add(tfColWidth);
        pnlConfigColumns.add(lblColHeight);
        pnlConfigColumns.add(tfColHeight);



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

        btnExcel.addActionListener(e -> {
            jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int choose = jfc.showOpenDialog(this);
            if (choose == jfc.getApproveButtonMnemonic()) {
                tfExcel.setText(jfc.getSelectedFile().getPath());
            }
        });

        btnRun.addActionListener(e -> {
            if (tfFolder.getText().equals(""))
                return;
            model.setWorkingDir(new File(tfFolder.getText()));
            model.setRatio(Integer.parseInt(tfLimRatio.getText()));
            model.setLimWidth(Integer.parseInt(tfLimWidth.getText()));
            model.setLimHeight(Integer.parseInt(tfLimHeight.getText()));

            model.setExcelFile(new File(tfExcel.getText()));
            model.setColRef(tfColRef.getText());
            model.setColSize(tfColSize.getText());
            model.setColWidth(tfColWidth.getText());
            model.setColHeight(tfColHeight.getText());

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
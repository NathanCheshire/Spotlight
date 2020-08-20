import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.*;
import javax.swing.border.LineBorder;


public class Spotlight {

    private String OutDir = "C:\\Users\\" + System.getProperty("user.name") + "\\Downloads\\Spotlight Images";

    public static void main(String[] args) {
        UIManager.put("ToolTip.background", new Color(39,40,34));
        UIManager.put("ToolTip.border", Color.black);
        UIManager.put("ToolTip.font", new Font("Tahoma",Font.BOLD,10));
        UIManager.put("ToolTip.foreground", new Color(85,85,255));

        new Spotlight();
    }

    private Spotlight() {
        JFrame MainFrame = new JFrame();

        MainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MainFrame.setSize(400,250);

        MainFrame.setTitle("Windows Spotlight Image Stealer");

        MainFrame.setResizable(false);

        MainFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("Logo.png")));

        JPanel ParentPanel = new JPanel();

        ParentPanel.setLayout(new GridLayout(4,1));

        JLabel Title = new JLabel("Windows Spotlight Image Stealer");

        Title.setFont(new Font("Impact", Font.BOLD, 20));

        Color gray = new Color(40, 40, 40);
        Title.setForeground(gray);

        JPanel TitlePanel = new JPanel();

        TitlePanel.setLayout(new FlowLayout());

        TitlePanel.add(Title, SwingConstants.CENTER);

        ParentPanel.add(TitlePanel);

        JLabel Description = new JLabel("<html>A Windows Tool by <font color=rgb(255,140,0)>Nate Cheshire</html>");

        Description.setFont(new Font("Arial Black", Font.BOLD, 15));

        Description.setForeground(gray);

        JPanel DescriptionPanel = new JPanel();

        DescriptionPanel.setLayout(new FlowLayout());

        DescriptionPanel.add(Description, SwingConstants.CENTER);

        ParentPanel.add(DescriptionPanel);

        JTextField OutputDir = new JTextField(28);

        OutputDir.setText(OutDir);

        OutputDir.setToolTipText("Directory to output files to (if it doesn't exist it will be created)");

        OutputDir.setFont(new Font("Segoe UI Black", Font.PLAIN, 10));

        OutputDir.setSelectionColor(new Color(204,153,0));

        OutputDir.setCaretColor(gray);

        OutputDir.setPreferredSize(new Dimension(300,25));

        Color navy = new Color(36, 48, 88);
        OutputDir.setBorder(new LineBorder(navy,3,false));

        JPanel DirPanel = new JPanel();

        DirPanel.setLayout(new FlowLayout());

        DirPanel.add(OutputDir, SwingConstants.CENTER);

        ParentPanel.add(DirPanel);

        JButton Steal = new JButton("Steal");

        Steal.setFocusPainted(false);

        Steal.setFont(new Font("Segoe UI Black", Font.BOLD, 14));

        Steal.setBackground(new Color(223,85,83));

        Steal.setForeground(Color.black);

        Steal.addActionListener(e -> {
            try {
                OutDir = OutputDir.getText();

                SpotlightCore();

                JOptionPane.showMessageDialog(null,"Spotlight files successfully stolen and outputed to: \n" + OutDir + "\"","Success",JOptionPane.INFORMATION_MESSAGE);

                System.exit(0);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error stack trace: " + ex, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });

        Steal.setPreferredSize(new Dimension(250,25));

        Steal.setBorder(new LineBorder(navy,3,false));

        JPanel StealPanel = new JPanel();

        StealPanel.setLayout(new FlowLayout());

        StealPanel.add(Steal, SwingConstants.CENTER);

        ParentPanel.add(StealPanel);

        ParentPanel.setBorder(new LineBorder(navy,10,false));

        MainFrame.add(ParentPanel);

        MainFrame.setLocationRelativeTo(null);

        MainFrame.repaint();

        MainFrame.revalidate();

        MainFrame.setVisible(true);
    }

    private void SpotlightCore() {
        try {
            //allow user to change this
            File storeSpotlights = new File(OutDir);

            if (!storeSpotlights.exists()) {
                storeSpotlights.mkdirs();
            } else {
                Delete(storeSpotlights);
            }

            File SystemDir = new File("C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Local\\Packages\\Microsoft.Windows.ContentDeliveryManager_cw5n1h2txyewy\\LocalState\\Assets");

            File[] files = SystemDir.listFiles();

            System.out.println(files[1]);

            for (int i = 0 ; i < files.length ; i++) {
                copyFile(files[i], new File(OutDir + "\\" + i + ".png"));
            }

            files = storeSpotlights.listFiles();

            for (File f: files) {
                f.renameTo(new File(f.getAbsolutePath().replace(f.getName(), "") + ".png"));
            }

            files = storeSpotlights.listFiles();

            for (File f: files) {
                BufferedImage img = ImageIO.read(f);

                if (img.getWidth() <= img.getHeight()) {
                    f.delete();
                }
            }
        }

        catch (Exception e) {
            StringWriter sw = new StringWriter();

            PrintWriter pw = new PrintWriter(sw);

            e.printStackTrace(pw);

            String error = sw.toString();

            JOptionPane.showMessageDialog(null, error, "", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void Delete(File dir) {
        File[] files = dir.listFiles();

        for (File file: files) {
            if (file.isDirectory()) {
                Delete(file);
            }

            file.delete();
        }
    }

    private  void copyFile(File sourceFile, File destDirectory) {
        try {
            Files.copy(sourceFile.toPath(), destDirectory.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }

        catch (Exception e) {
            StringWriter sw = new StringWriter();

            PrintWriter pw = new PrintWriter(sw);

            e.printStackTrace(pw);

            String error = sw.toString();

            JOptionPane.showMessageDialog(null, error, "", JOptionPane.ERROR_MESSAGE);
        }
    }
}

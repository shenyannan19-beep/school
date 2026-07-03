package school.jiemian;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 主窗口类，应用程序的图形化入口
 */
public class MainFrame extends JFrame {
    /**
     * 数据资源对象
     */
    public static Source scr = Source.factory();
    
    /**
     * 内容面板，用于切换不同功能界面
     */
    private CardLayout cardLayout;
    private JPanel contentPanel;
    
    /**
     * 主菜单面板
     */
    private MainMenuPanel mainMenuPanel;
    
    /**
     * 构造函数
     */
    public MainFrame() {
        // 设置窗口标题
        setTitle("校园景点导航系统");
        
        // 设置窗口大小
        setSize(800, 600);
        
        // 设置窗口居中显示
        setLocationRelativeTo(null);
        
        // 设置默认关闭操作
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        // 添加窗口关闭事件监听器，用于保存数据
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saveData();
                System.exit(0);
            }
        });
        
        // 初始化界面组件
        initComponents();
        
        // 设置窗口可见
        setVisible(true);
    }
    
    /**
     * 初始化界面组件
     */
    private void initComponents() {
        // 创建卡片布局和内容面板
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        
        // 创建主菜单面板
        mainMenuPanel = new MainMenuPanel(this);
        contentPanel.add(mainMenuPanel, "MainMenu");
        
        // 将内容面板添加到窗口
        add(contentPanel, BorderLayout.CENTER);
    }
    
    /**
     * 切换到指定的面板
     * @param panelName 面板名称
     */
    public void showPanel(String panelName) {
        cardLayout.show(contentPanel, panelName);
    }
    
    /**
     * 添加面板到内容面板
     * @param panel 面板对象
     * @param panelName 面板名称
     */
    public void addPanel(JPanel panel, String panelName) {
        contentPanel.add(panel, panelName);
    }
    
    /**
     * 保存数据
     */
    public void saveData() {
        scr.saveSource();
        JOptionPane.showMessageDialog(this, "数据已保存", "提示", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * 主函数，应用程序入口
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        // 使用SwingUtilities.invokeLater确保在EDT线程中创建界面
        SwingUtilities.invokeLater(() -> new MainFrame());
    }
}
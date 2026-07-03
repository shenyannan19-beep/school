package school.jiemian;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 主菜单面板，包含所有功能按钮
 */
public class MainMenuPanel extends JPanel {
    private MainFrame mainFrame;
    
    /**
     * 构造函数
     * @param mainFrame 主窗口对象
     */
    public MainMenuPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        initComponents();
    }
    
    /**
     * 初始化界面组件
     */
    private void initComponents() {
        // 设置布局
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // 添加校园地图图片
        try {
            ImageIcon mapIcon = new ImageIcon("res/campus_map.png.jpg");
            // 获取原始图片
            Image originalImage = mapIcon.getImage();
            // 设置最大高度为300像素，保持宽高比
            int maxHeight = 300;
            int originalWidth = originalImage.getWidth(null);
            int originalHeight = originalImage.getHeight(null);
            
            // 计算新的尺寸，保持宽高比
            int newWidth = originalWidth;
            int newHeight = originalHeight;
            if (originalHeight > maxHeight) {
                newHeight = maxHeight;
                newWidth = (originalWidth * maxHeight) / originalHeight;
            }
            
            // 缩放图片
            Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            
            JLabel mapLabel = new JLabel(scaledIcon);
            // 居中显示图片
            mapLabel.setHorizontalAlignment(JLabel.CENTER);
            add(mapLabel, BorderLayout.NORTH);
        } catch (Exception e) {
            // 如果图片加载失败，显示文本提示
            JLabel errorLabel = new JLabel("校园地图图片未找到，请将地图图片命名为campus_map.png.jpg并放在res目录下", JLabel.CENTER);
            errorLabel.setFont(new Font("微软雅黑", Font.PLAIN, 16));
            errorLabel.setForeground(Color.RED);
            add(errorLabel, BorderLayout.NORTH);
        }
        
        // 创建按钮
        JButton btnDisplayJingdian = new JButton("显示景点");
        JButton btnAddJingdian = new JButton("添加景点");
        JButton btnDelJingdian = new JButton("删除景点");
        JButton btnDisplayLujing = new JButton("显示路径");
        JButton btnModifyLujing = new JButton("修改路径");
        JButton btnFindPath = new JButton("查询路径");
        JButton btnSaveData = new JButton("保存数据");
        JButton btnExit = new JButton("退出系统");
        
        // 设置按钮字体
        Font buttonFont = new Font("微软雅黑", Font.PLAIN, 16);
        btnDisplayJingdian.setFont(buttonFont);
        btnAddJingdian.setFont(buttonFont);
        btnDelJingdian.setFont(buttonFont);
        btnDisplayLujing.setFont(buttonFont);
        btnModifyLujing.setFont(buttonFont);
        btnFindPath.setFont(buttonFont);
        btnSaveData.setFont(buttonFont);
        btnExit.setFont(buttonFont);
        
        // 添加按钮点击事件监听器
        btnDisplayJingdian.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 切换到显示景点面板
                DisplayJingdianPanel panel = new DisplayJingdianPanel(mainFrame);
                mainFrame.addPanel(panel, "DisplayJingdian");
                mainFrame.showPanel("DisplayJingdian");
            }
        });
        
        btnAddJingdian.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 切换到添加景点面板
                AddJingdianPanel panel = new AddJingdianPanel(mainFrame);
                mainFrame.addPanel(panel, "AddJingdian");
                mainFrame.showPanel("AddJingdian");
            }
        });
        
        btnDelJingdian.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 切换到删除景点面板
                DelJingdianPanel panel = new DelJingdianPanel(mainFrame);
                mainFrame.addPanel(panel, "DelJingdian");
                mainFrame.showPanel("DelJingdian");
            }
        });
        
        btnDisplayLujing.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 切换到显示路径面板
                DisplayLujingPanel panel = new DisplayLujingPanel(mainFrame);
                mainFrame.addPanel(panel, "DisplayLujing");
                mainFrame.showPanel("DisplayLujing");
            }
        });
        
        btnModifyLujing.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 切换到修改路径面板
                ModifyLujingPanel panel = new ModifyLujingPanel(mainFrame);
                mainFrame.addPanel(panel, "ModifyLujing");
                mainFrame.showPanel("ModifyLujing");
            }
        });
        
        btnFindPath.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 切换到查询路径面板
                FindPathPanel panel = new FindPathPanel(mainFrame);
                mainFrame.addPanel(panel, "FindPath");
                mainFrame.showPanel("FindPath");
            }
        });
        
        btnSaveData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 保存数据
                mainFrame.saveData();
            }
        });
        
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 保存数据并退出系统
                mainFrame.saveData();
                System.exit(0);
            }
        });
        
        // 创建按钮面板，使用GridLayout放置按钮
        JPanel buttonPanel = new JPanel(new GridLayout(4, 2, 20, 20));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        
        // 添加按钮到面板
        buttonPanel.add(btnDisplayJingdian);
        buttonPanel.add(btnAddJingdian);
        buttonPanel.add(btnDelJingdian);
        buttonPanel.add(btnDisplayLujing);
        buttonPanel.add(btnModifyLujing);
        buttonPanel.add(btnFindPath);
        buttonPanel.add(btnSaveData);
        buttonPanel.add(btnExit);
        
        // 将按钮面板添加到主面板的中间
        add(buttonPanel, BorderLayout.CENTER);
    }
}
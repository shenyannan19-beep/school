package school.jiemian;

import school.functions.Jingdian;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 显示景点面板
 */
public class DisplayJingdianPanel extends JPanel {
    private MainFrame mainFrame;
    
    /**
     * 构造函数
     * @param mainFrame 主窗口对象
     */
    public DisplayJingdianPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        initComponents();
    }
    
    /**
     * 初始化界面组件
     */
    private void initComponents() {
        // 设置布局
        setLayout(new BorderLayout());
        
        // 创建标题标签
        JLabel titleLabel = new JLabel("景点列表", JLabel.CENTER);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);
        
        // 创建文本区域用于显示景点信息
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        
        // 添加滚动面板
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);
        
        // 显示景点信息
        if (MainFrame.scr.jd.isEmpty()) {
            textArea.setText("没有景点信息");
        } else {
            StringBuilder sb = new StringBuilder();
            for (Jingdian jd : MainFrame.scr.jd) {
                sb.append(String.format("编号: %s\n", jd.ID));
                sb.append(String.format("名称: %s\n", jd.name));
                sb.append(String.format("校区: %s\n", jd.section));
                sb.append(String.format("介绍: %s\n", jd.introduce));
                sb.append("\n-----------------------------------\n\n");
            }
            textArea.setText(sb.toString());
        }
        
        // 创建返回按钮
        JButton btnBack = new JButton("返回主菜单");
        btnBack.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.showPanel("MainMenu");
            }
        });
        
        // 创建按钮面板
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnBack);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
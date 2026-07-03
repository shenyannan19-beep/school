package school.jiemian;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 显示路径面板
 */
public class DisplayLujingPanel extends JPanel {
    private MainFrame mainFrame;
    
    /**
     * 构造函数
     * @param mainFrame 主窗口对象
     */
    public DisplayLujingPanel(MainFrame mainFrame) {
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
        JLabel titleLabel = new JLabel("路径列表", JLabel.CENTER);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);
        
        // 创建文本区域用于显示路径信息
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        
        // 添加滚动面板
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);
        
        // 显示路径信息
        if (MainFrame.scr.dt.isEmpty()) {
            textArea.setText("没有路径信息");
        } else {
            StringBuilder sb = new StringBuilder();
            for (String key : MainFrame.scr.dt.keySet()) {
                String[] ids = key.split("##");
                String startName = getIdToName(ids[0]);
                String endName = getIdToName(ids[1]);
                double distance = MainFrame.scr.dt.get(key);
                
                sb.append(String.format("%s (%s) → %s (%s)\n", startName, ids[0], endName, ids[1]));
                sb.append(String.format("距离: %.2f 米\n", distance));
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
    
    /**
     * 根据景点编号获取景点名称
     * @param id 景点编号
     * @return 景点名称
     */
    private String getIdToName(String id) {
        for (int i = 0; i < MainFrame.scr.jd.size(); i++) {
            if (MainFrame.scr.jd.get(i).ID.equals(id)) {
                return MainFrame.scr.jd.get(i).name;
            }
        }
        return "未知景点";
    }
}
package school.jiemian;

import school.functions.Jingdian;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 删除景点面板
 */
public class DelJingdianPanel extends JPanel {
    private MainFrame mainFrame;
    
    // 组件
    private JComboBox<String> jdComboBox;
    private JTextArea detailTextArea;
    
    /**
     * 构造函数
     * @param mainFrame 主窗口对象
     */
    public DelJingdianPanel(MainFrame mainFrame) {
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
        JLabel titleLabel = new JLabel("删除景点", JLabel.CENTER);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);
        
        // 创建表单面板
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // 选择景点
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblSelect = new JLabel("选择景点:");
        lblSelect.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        formPanel.add(lblSelect, gbc);
        
        gbc.gridx = 1;
        // 创建景点下拉列表
        String[] jdItems = new String[MainFrame.scr.jd.size()];
        for (int i = 0; i < MainFrame.scr.jd.size(); i++) {
            Jingdian jd = MainFrame.scr.jd.get(i);
            jdItems[i] = String.format("%s - %s", jd.ID, jd.name);
        }
        jdComboBox = new JComboBox<>(jdItems);
        jdComboBox.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        jdComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showJingdianDetail();
            }
        });
        formPanel.add(jdComboBox, gbc);
        
        // 景点详情
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.NORTH;
        JLabel lblDetail = new JLabel("景点详情:");
        lblDetail.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        formPanel.add(lblDetail, gbc);
        
        gbc.gridy = 2;
        detailTextArea = new JTextArea(8, 40);
        detailTextArea.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        detailTextArea.setEditable(false);
        detailTextArea.setLineWrap(true);
        detailTextArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(detailTextArea);
        formPanel.add(scrollPane, gbc);
        
        add(formPanel, BorderLayout.CENTER);
        
        // 显示第一个景点的详情
        if (MainFrame.scr.jd.size() > 0) {
            showJingdianDetail();
        }
        
        // 创建按钮面板
        JPanel buttonPanel = new JPanel();
        JButton btnDelete = new JButton("删除");
        btnDelete.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        JButton btnBack = new JButton("返回");
        btnBack.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnBack);
        add(buttonPanel, BorderLayout.SOUTH);
        
        // 添加按钮事件监听器
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteJingdian();
            }
        });
        
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.showPanel("MainMenu");
            }
        });
    }
    
    /**
     * 显示选中景点的详情
     */
    private void showJingdianDetail() {
        int index = jdComboBox.getSelectedIndex();
        if (index >= 0 && index < MainFrame.scr.jd.size()) {
            Jingdian jd = MainFrame.scr.jd.get(index);
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("编号: %s\n", jd.ID));
            sb.append(String.format("名称: %s\n", jd.name));
            sb.append(String.format("校区: %s\n", jd.section));
            sb.append(String.format("介绍: %s\n", jd.introduce));
            detailTextArea.setText(sb.toString());
        }
    }
    
    /**
     * 删除选中的景点
     */
    private void deleteJingdian() {
        int index = jdComboBox.getSelectedIndex();
        if (index >= 0 && index < MainFrame.scr.jd.size()) {
            // 确认删除
            int result = JOptionPane.showConfirmDialog(this, "确定要删除该景点吗？", "确认删除", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                Jingdian jd = MainFrame.scr.jd.remove(index);
                
                // 同时删除与该景点相关的路径
                String[] keysToRemove = new String[MainFrame.scr.dt.size()];
                int count = 0;
                for (String key : MainFrame.scr.dt.keySet()) {
                    if (key.startsWith(jd.ID + "##") || key.endsWith("##" + jd.ID)) {
                        keysToRemove[count++] = key;
                    }
                }
                for (int i = 0; i < count; i++) {
                    MainFrame.scr.dt.remove(keysToRemove[i]);
                }
                
                JOptionPane.showMessageDialog(this, "景点删除成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                
                // 更新界面
                mainFrame.showPanel("MainMenu");
            }
        }
    }
}
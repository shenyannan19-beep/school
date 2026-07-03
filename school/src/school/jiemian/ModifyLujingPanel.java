package school.jiemian;

import school.functions.Jingdian;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 修改路径面板
 */
public class ModifyLujingPanel extends JPanel {
    private MainFrame mainFrame;
    
    // 组件
    private JComboBox<String> startComboBox;
    private JComboBox<String> endComboBox;
    private JTextField distanceField;
    
    /**
     * 构造函数
     * @param mainFrame 主窗口对象
     */
    public ModifyLujingPanel(MainFrame mainFrame) {
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
        JLabel titleLabel = new JLabel("修改路径", JLabel.CENTER);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);
        
        // 创建表单面板
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(15, 10, 15, 10);
        
        // 起始景点
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblStart = new JLabel("起始景点:");
        lblStart.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        formPanel.add(lblStart, gbc);
        
        gbc.gridx = 1;
        // 创建起始景点下拉列表
        String[] jdItems = new String[MainFrame.scr.jd.size()];
        for (int i = 0; i < MainFrame.scr.jd.size(); i++) {
            Jingdian jd = MainFrame.scr.jd.get(i);
            jdItems[i] = String.format("%s - %s", jd.ID, jd.name);
        }
        startComboBox = new JComboBox<>(jdItems);
        startComboBox.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        formPanel.add(startComboBox, gbc);
        
        // 到达景点
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel lblEnd = new JLabel("到达景点:");
        lblEnd.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        formPanel.add(lblEnd, gbc);
        
        gbc.gridx = 1;
        endComboBox = new JComboBox<>(jdItems);
        endComboBox.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        // 默认选择下一个景点
        if (MainFrame.scr.jd.size() > 1) {
            endComboBox.setSelectedIndex(1);
        }
        formPanel.add(endComboBox, gbc);
        
        // 路径长度
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel lblDistance = new JLabel("路径长度(米):");
        lblDistance.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        formPanel.add(lblDistance, gbc);
        
        gbc.gridx = 1;
        distanceField = new JTextField(20);
        distanceField.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        formPanel.add(distanceField, gbc);
        
        add(formPanel, BorderLayout.CENTER);
        
        // 创建按钮面板
        JPanel buttonPanel = new JPanel();
        JButton btnModify = new JButton("修改");
        btnModify.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        JButton btnBack = new JButton("返回");
        btnBack.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        
        buttonPanel.add(btnModify);
        buttonPanel.add(btnBack);
        add(buttonPanel, BorderLayout.SOUTH);
        
        // 添加按钮事件监听器
        btnModify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifyLujing();
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
     * 修改路径
     */
    private void modifyLujing() {
        // 获取起始和结束景点
        String startItem = (String) startComboBox.getSelectedItem();
        String endItem = (String) endComboBox.getSelectedItem();
        
        if (startItem == null || endItem == null) {
            JOptionPane.showMessageDialog(this, "请选择景点", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // 提取景点编号
        String startId = startItem.split(" - ")[0];
        String endId = endItem.split(" - ")[0];
        
        // 不能选择相同的景点
        if (startId.equals(endId)) {
            JOptionPane.showMessageDialog(this, "起始景点和到达景点不能相同", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // 获取路径长度
        String distanceText = distanceField.getText().trim();
        if (distanceText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请输入路径长度", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            double distance = Double.parseDouble(distanceText);
            
            // 生成路径键
            String key = String.format("%s##%s", startId, endId);
            
            if (distance < 0) {
                // 删除路径
                if (MainFrame.scr.dt.containsKey(key)) {
                    MainFrame.scr.dt.remove(key);
                    JOptionPane.showMessageDialog(this, "路径已删除", "提示", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "该路径不存在", "提示", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                // 添加或修改路径
                MainFrame.scr.dt.put(key, distance);
                JOptionPane.showMessageDialog(this, "路径已保存", "提示", JOptionPane.INFORMATION_MESSAGE);
            }
            
            // 清空输入
            distanceField.setText("");
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "请输入有效的数字", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
}

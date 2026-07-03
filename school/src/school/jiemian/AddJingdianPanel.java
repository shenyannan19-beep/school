package school.jiemian;

import school.functions.Jingdian;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 添加景点面板
 */
public class AddJingdianPanel extends JPanel {
    private MainFrame mainFrame;
    
    // 文本框组件
    private JTextField txtId;
    private JTextField txtName;
    private JTextField txtSection;
    private JTextArea txtIntroduce;
    
    /**
     * 构造函数
     * @param mainFrame 主窗口对象
     */
    public AddJingdianPanel(MainFrame mainFrame) {
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
        JLabel titleLabel = new JLabel("添加景点", JLabel.CENTER);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);
        
        // 创建表单面板
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // 景点编号
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblId = new JLabel("景点编号:");
        lblId.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        formPanel.add(lblId, gbc);
        
        gbc.gridx = 1;
        txtId = new JTextField(20);
        txtId.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        // 设置默认编号
        txtId.setText(String.format("%04d", MainFrame.scr.jd.size() + 1));
        formPanel.add(txtId, gbc);
        
        // 景点名称
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel lblName = new JLabel("景点名称:");
        lblName.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        formPanel.add(lblName, gbc);
        
        gbc.gridx = 1;
        txtName = new JTextField(20);
        txtName.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        formPanel.add(txtName, gbc);
        
        // 所在校区
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel lblSection = new JLabel("所在校区:");
        lblSection.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        formPanel.add(lblSection, gbc);
        
        gbc.gridx = 1;
        txtSection = new JTextField(20);
        txtSection.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        formPanel.add(txtSection, gbc);
        
        // 景点介绍
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        JLabel lblIntroduce = new JLabel("景点介绍:");
        lblIntroduce.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        formPanel.add(lblIntroduce, gbc);
        
        gbc.gridx = 1;
        gbc.weighty = 1.0;
        txtIntroduce = new JTextArea(5, 20);
        txtIntroduce.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        txtIntroduce.setLineWrap(true);
        txtIntroduce.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(txtIntroduce);
        formPanel.add(scrollPane, gbc);
        
        add(formPanel, BorderLayout.CENTER);
        
        // 创建按钮面板
        JPanel buttonPanel = new JPanel();
        JButton btnAdd = new JButton("添加");
        btnAdd.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        JButton btnBack = new JButton("返回");
        btnBack.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnBack);
        add(buttonPanel, BorderLayout.SOUTH);
        
        // 添加按钮事件监听器
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addJingdian();
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
     * 添加景点
     */
    private void addJingdian() {
        // 获取用户输入
        String id = txtId.getText().trim();
        String name = txtName.getText().trim();
        String section = txtSection.getText().trim();
        String introduce = txtIntroduce.getText().trim();
        
        // 验证输入
        if (id.isEmpty() || name.isEmpty() || section.isEmpty() || introduce.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请填写所有字段", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // 检查编号是否已存在
        for (Jingdian jd : MainFrame.scr.jd) {
            if (jd.ID.equals(id)) {
                JOptionPane.showMessageDialog(this, "景点编号已存在", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        
        // 创建景点对象
        Jingdian jd = new Jingdian(id, name, section, introduce);
        
        // 添加到景点列表
        MainFrame.scr.jd.add(jd);
        
        JOptionPane.showMessageDialog(this, "景点添加成功", "提示", JOptionPane.INFORMATION_MESSAGE);
        
        // 清空文本框
        txtId.setText(String.format("%04d", MainFrame.scr.jd.size() + 1));
        txtName.setText("");
        txtSection.setText("");
        txtIntroduce.setText("");
    }
}
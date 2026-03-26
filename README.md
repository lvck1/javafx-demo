# 贪吃蛇游戏

基于 JavaFX 开发的经典贪吃蛇游戏，支持多种难度设置和积分榜功能。

## 功能特性

### 主菜单
- 开始游戏
- 积分榜
- 设置
- 退出游戏

### 游戏玩法
- 使用方向键或 WASD 控制蛇的移动
- 按 P 键暂停/继续游戏
- 吃到食物得分并增长
- 碰到墙壁或自己身体游戏结束

### 难度设置
- **初级难度**: 可穿越墙壁（左进右出，上进下出）
- **中级难度**: 碰到墙壁死亡
- **高级难度**: 碰到墙壁死亡，速度更快

### 积分榜
- 保存历史 Top 10 积分
- 显示得分时间和难度等级
- 支持清空积分记录

## 技术栈

- **Java**: JDK 21
- **构建工具**: Maven 3
- **UI框架**: JavaFX 21
- **JSON处理**: Gson

## 运行要求

- JDK 21 或更高版本
- Maven 3.6 或更高版本

## 构建和运行

### 使用 Maven 运行
```bash
mvn clean javafx:run
```

### 打包成可执行 JAR
```bash
mvn clean package
```

### 运行打包后的 JAR
```bash
java -jar target/javafx-demo-1.0-SNAPSHOT.jar
```

## 项目结构

```
src/
├── main/
│   ├── java/com/example/demo/
│   │   ├── App.java                    # 应用程序入口
│   │   ├── controller/                 # 控制器
│   │   │   ├── MainMenuController.java
│   │   │   ├── GameController.java
│   │   │   ├── SettingsController.java
│   │   │   └── ScoreboardController.java
│   │   ├── model/                      # 数据模型
│   │   │   ├── Snake.java
│   │   │   ├── Food.java
│   │   │   └── Score.java
│   │   ├── service/                    # 业务逻辑
│   │   │   └── GameEngine.java
│   │   └── util/                       # 工具类
│   │       └── ScoreManager.java
│   └── resources/
│       ├── fxml/                       # 界面布局
│       │   ├── mainmenu.fxml
│       │   ├── game.fxml
│       │   ├── settings.fxml
│       │   └── scoreboard.fxml
│       └── css/                        # 样式文件
│           └── style.css
└── test/
    └── java/com/example/demo/         # 测试代码
```

## 游戏控制

| 按键 | 功能 |
|------|------|
| ↑ / W | 向上移动 |
| ↓ / S | 向下移动 |
| ← / A | 向左移动 |
| → / D | 向右移动 |
| P | 暂停/继续 |

## 积分规则

- 每吃到一个食物得 **10 分**
- 游戏结束时自动保存积分
- 积分榜按分数从高到低排序
- 只保留 Top 10 记录

## 许可证

MIT License

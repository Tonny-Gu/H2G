# Project H2G

This project has nothing right. 
This project has nothing left. 

## Important TIPS

运行方法：将UsedData文件夹中的Data.json放置在根目录下，并运行ThreadManager.java。

Data.json囊括了所有可定制成分，架构清晰，具有一般英语水平和理解能力的使用者可自行修改。

更多详见项目GitHub地址https://github.com/Tonny-Gu/H2G

### Lock 
| Name | Description | Status |
| --- | --- | --- |
| BarDrawingTutor.java | 这里面的代码已经快要成为意大利面了 | Locked |
| Data.json | 所有数据json全部按照Data.json书写，不再修改 | Locked |

### TODO LIST

项目结题。最后的Todo List留作纪念。

项目尾期，按照重要性和难易度简单区分扫尾工作：

| 工作内容                                                     | 重要性 | 复杂度 |
| ------------------------------------------------------------ | ------ | ------ |
|显示图例，增加显示图例的开关和图例显示的的坐标、缩放因数，并完成json适配|SS|中|
| 制作barUI的json，并且在DynamicLoader中加入对应的BarBasicSkinStyle类 | S      | 低     |
| 寻找数据，制作data.json                                      | S      | 低     |
| 对于TaskA,TaskB,TaskC三个任务最终确定三组json                | S      | 低     |
| 准备答辩PPT                                                  | A      | 中     |
| 非纯色高级skin的代码编写                                     | B      | 中高  |


### Milestone

| Contributor | Content | DoneDate |
| --- | --- | --- |
| All members | 举办第一次组会 | 12.7 |
| All members | 举办第二次组会 | 12.14 |
| All members | 答辩，结题 | 12.18 |

# Data Structure

| Path | Class Name | Description |
| --- | --- | --- |
| \ | HistogramData | 数据模板 |
| \ | CanvaStyle | 样式模板 |
| \ | rawData | 数据 |
| \ | BarBasicSkinStyle | 皮肤 |

# Class

## DataLoader
负责读取data.json内的数据部分，处理为rawData移交给ThreadManager。

## LegendDrawer
负责通过data.json内的部分数据生成图例。

| Primary API Name | Parameter | Description |
| --- | --- | --- |
| loadConfig() | String pattern | **必须**，导入数据并解析 |
| getLegend() | / | 获得一张BufferImage格式的完整图例 |
| getBarLegend() | int id | 获得持有某id的，单独bar的图例 |

## FrameCreator
基于Reference的HistogramA的单帧生成器

### Update Log
#### Version 2 rev.B (2018.12.15)
1. 接入StackedBar的绘制方法
#### Version 2 rev.A (2018.12.13)
1. 完成BarDrawingTutor接口的对接
2. 完成坐标系旋转相关代码
#### Version 1 rev.B+ (2018.12.1)
#### Version 1 rev.B (2018.11.30)
修复Bug
#### Version 1 rev.A (2018.11.29)

## RulerDrawingTutor
自动计算rulerGrade和rulerStep以指导FrameCreator绘制Ruler

| Primary API Name | Parameter | Description |
| --- | --- | --- |
| RulerDrawingTutor() | CanvaStyle canvaStyle, HistogramData histogramData | 实例化，传入比例尺和最大刻度数量 |
| setYmaxValue() | double yMaxValue | 指定刻度的最大值 |
| getRulerStep() | | 自适应并计算rulerStep |
| getRulerGrade() | | 自适应并计算rulerGrade |

### Note:自适应只适应增大的情况，不适应缩小的情况
### Update Log
#### Version 1 rev.C (2018.12.14)
1. 调整接口

## BarDrawingTutor
指导FrameCreator绘制Bar

### Primary API Reference 

| Primary API Name | Parameter | Description |
| --- | --- | --- |
| hasNext() || 判断当前帧是否还有下一个需要绘制的Bar |
| next() || 切换到下一个需要绘制的Bar |
| getLocation() || 获取Bar的坐标 |
| getBarImg() || 获得Bar的BufferedImage，已经预设好皮肤，透明度和长度 |
其余的方法请查看源码

### Related Class

| Related Class | Description |
| --- | --- |
| BarDrawingHelper | 用于生成特定帧的BarDrawingTutor |
| Interpolator | 生成数值和坐标的插值，调用BarLayoutDesigner生成坐标的插值 |
| BarLayoutDesigner | 队列化处理Bar交换事件，避免多重交换，生成Bar的横坐标 |
| BarSwaper | 利用非线性函数计算和平移Bar的坐标以实现Bar的交换 |
| BarSwapStatus | 含两个BarLocation和交换进度的数据结构 |
| BarLocation | 含Bar ID,所在图层和坐标的数据结构 |
| StackedBarDrawingHelper | 为StackedBar调整的BarDrawingHelper |
| StackedBarDrawingTutor | 为StackedBar调整的BarDrawingTutor |

### InnerClass Primary API Reference

| Class Name | API Name | Description |
| --- | --- | --- |
| BarDrawingHelper | BarDrawingHelper | 初始化 |
| | getTutor | 获得特定帧的BarDrawingTutor |
| Interplator | parseBarPattern | 解析BarPattern(在CanvaStyle里定义),分配Bar初始坐标 |
| | interpolateBarValue | 对值插值 |
| | interpolateBarLocation | 调用BarLocationDesigner对坐标插值 |
| | sortAndSwapBar | 优化的冒泡排序并产生交换事件传递给BarLocationDesigner |
| BarLayoutDesigner | swapBars | 设置交换事件 |
| | getTransparency | 根据交换进度计算透明度 |
| | getLayout | 调用BarSwaper获得当前帧所有Bar的坐标 |
| | nextFrame | 切换到下一帧 |
| | checkWaitingQueue | 检查等待队列并激活等待态的事件 |
| BarSwaper | getDisplace | 计算Bar的偏移(更改插值函数同时需要调整构造器) |
| | getProgress | 获取Bar的交换进度 |
| | getCurrentLocation | 获得Bar的当前坐标(对调用顺序无要求) |


### Update Log
#### Version 2 rev.C (2018.12.15)
1. 完成StackedBar特化BarDrawingHelper和BarDrawingTutor的编写
2. 修复起始值不为0时出现的Bug
3. 为Interpolator添加用于辅助绘制StackedBar的开关
#### Version 2 rev.B (2018.12.14)
1. 重新编写构造方法，使得调用方法更合理
#### Version 2 rev.A (2018.12.9)
1. 重新设计交换事件冲突处理机制
#### Version 1 rev.C (2018.12.7)
#### Version 1 rev.B (2018.12.6)
#### Version 1 rev.A (2018.12.2)


## ThreadManager
线程管理器兼Launcher

| Primary Variable Name | Description |
| --- | --- |
| double[][] rawData | 第一维存储不同的Bar的信息，第二维存储不同帧的信息 |
| ConcurrentLinkedQueue<BufferedImage> buffer | 线程安全的FIFO缓冲区 |

| InnerClass | Description |
| --- | --- |
| ImagePlayer | 由StdDraw的JFrame部分分离出来的ImageBuffered播放器，并且借助Timer运行在其他线程上 |

#### InnerClass Primary API Reference
| API Name | Parameter | Description |
| --- | --- | --- |
| show() | BufferedImage offscreenImage | 显示BufferedImage的内容 |
| ImagePlayer() | ConcurrentLinkedQueue<BufferedImage> buffer, int[] bgSize | 传入FIFO缓冲区(线程安全)，初始化播放器 |

### Update Log
#### Version 1 rev.C (2018.12.15)
1. 接入StackedBar的绘制方法
#### Version 1 rev.B (2018.12.14)
1. 整理代码
#### Version 1 rev.A (2018.12.8)
1. 完成基本处理流程的设计，基本实现动画化

## CoordProjecter
坐标转换工具

### API Reference
| API Name | Parameter | Description |
| --- | --- | --- |
| CoordProjecter() | SigDraw base, SigDraw img, double xCentreOfImg, double yCentreOfImg  | xCentreOfImg & yCentreOfImg 是img的图片中心在base参考系下base上的坐标 |
| getX() & getY() | double x/y | 将img上某点在img参考系下的坐标转换为在base参考系下base上的坐标 |


## BarGenerator
柱状图生成器抽象类

| SubClass | Description |
| --- | --- |
| BarBasicSkin | 基本样式 |
| ~~BarFlatUISkin~~ | ~~采用扁平化配色方案的测试样式~~ |

| Related Class | Description |
| --- | --- |
| BarBasicSkinStyle | BarBasicSkin的样式表 |
| DynamicLoader | 预读取Json文件，集中创建BarGenerator，降低IO开销 |
### API v2 Reference
| Class | API Name | Parameter | Description |
| --- | --- | --- | --- |
| BarGenerator | BarGenerator() | int[] barSize, double[] scale, boolean rotated | 构造器，传入bar尺寸，比例尺，旋转(true水平，false垂直) |
| | setScale() | double[] scale | 修改baseImg的比例尺，自动识别旋转方向 |
| | getBarChart() | int frame ,String text, double... val | 获取Bar的BufferedImage，传入帧序号，顶端显示文字，Bar对应值（可变长度，应产生叠加效果） |
| DynamicLoader | static{} | | 在静态初始化的时候读入并转化Json文件到内存 |
| | get() | String skinName, int[] barSize, double[] scale, boolean rotated | 创建并返回一个BarGenerator，skinName是Bar的皮肤名，与CanvaStyle里barSkin对应 |
### ~~API v1 Reference~~
1. ~~BarGenerator(int[] barSize, double[] scale) barSize:生成图像的尺寸 scale:柱状图的最小值和最大值~~
2. ~~BufferedImage getBarchart(int frame, double val1, double val2) 生成Bar图像~~
3. ~~void loadConfig(String filename) 载入预先配置文件~~

### Specification
1. 基于BasicSkin的配色方案，直接写成Json文件并由DynamicLoader作为BarBasicSkinStyle读入，同时在DynamicLoader里写入SkinName和实例化的方法
2. BarGenerator需要读入的静态文件需要在DynamicLoader里预读入到内存，降低IO开销
3. 可变参数的参数数量不做要求，带动画效果的BarGenerator可以只考虑一个参数的情况

### Update Log
#### Version 2 rev.B (2018.12.15)
1. 修复起始值不为0时出现的Bug

#### Version 2 rev.A (2018.12.12)
0. 重新修订BarGenerator的设计规范
1. 增加使用扁平化配色方案的BarFlatUISkin样式
2. 通过透明化测试
3. 修复BarBasicSkin存在的一处bug
4. 完成DynamicLoader的设计
5. 完成BarBasicSkinStyle的设计

#### Version 1 rev.B (2018.12.1)

1. 增加了基本样式对json数据的支持
2. 修改了部分抽象类的定义以支持json读取

#### Version 1 rev.A (2018.11.30)
1. 定义抽象类
2. 完成基本样式(BarBasicSkin)的编写



## HistogramData & DataLoader & CanvaStyle

数据相关的三个类，分别对应数据样式、数据内容、模板样式。均已通过json实现数据的调配。

### Update Log

#### Version 3 (2018.12.16)

json结构重大修订，并适配了新的json结构。

#### Version 2 (2018.12.15)

1. 合并所有数据相关的json为 "Data.json" 

2. 对新增的参数增加json支持

#### Version 1 rev.D (2018.12.15)

1. 添加参数

#### Version 1 rev.C (2018.12.14)

1. 添加参数

#### Version 1 rev.B (2018.12.1)

1. 修复颜色自定义无效的bug
2. 代码架构优化
3. json模板结构优化，并统一命名规范

#### Version 1 rev.A (2018.11.30)

使用json获取CanvaStyle数据



## ConfigLoader

ConfigLoader基于Json库的辅助程序，简化了读取操作

### API Reference
1. ConfigLoader(String fileName) 构造器
2. get...(String path) 读取指定位置的值 
   1. 基础类均可直接读取
   2. 复杂类的拓展模板模仿getDoubleArray()函数
   3. getTColor()已过时
3. set...(Object init, String path) 读取指定位置的值，若json表中不存在这个值，将会返回指定的init，同时在控制台输出json缺省提醒。

### Syntax of path
| 符号 | 含义 | 适用类 |
| --- | --- | --- |
| . | 分隔符 | 无 |
| Digit | List的下标 | JsonArray |
| Token | Map的Key | JsonObject |

### Update Log

#### Version 10 (2018.12.16)

增加了对默认值的支持。从此版本开始，项目中所有json处理都由get...(String pattern)更改为set..(Object init, String pattern)。

#### Version 1 rev.C (2018.12.15)

增加了获取两种基本类的方法

#### Version 1 rev.B (2018.12.1)

增加了获取两种特殊类的方法

#### Version 1 rev.A (2018.11.28)



## SigDraw

SigDraw是基于StdDraw魔改的产物

### API Reference
| Extra API Name | Description |
| --- | --- |
| SigDraw() | 构造器 |
| enableAntialiasing() | 启动反锯齿(默认开启) |
| disableAntialiasing() | 关闭反锯齿 |
| getBuffImg() | 获取内存中的图片(BufferedImage类型) |
| getScaledImage() | 获取缩放后的图片(BufferedImage类型) |
| loadImage() | 从本地文件载入图片(基于getImage(),同时废除该函数) |
| setBuffImg() | 重设内存缓存的图片 |
| setScaleUnaltered() | 离散化坐标系 |
| getSubImage() | 截取图像 |
| enableTransparent() | 启动全局alpha通道绘图模式 |

| Modify API Name | Description |
| --- | --- |
| picture() | 修改为只能载入BufferedImage图像 |
| init() | 修改启动流程，优化嵌套渲染的情况 |
| setCanvasSize() | 修改执行流程 |

### Update Log

#### Version 2 (2018.12.12)

1. 精简代码
2. 增加全局alpha通道绘图模式开关

#### Version 1 rev.C (2018.11.30)
1. 移除高分辨率相关函数
2. 增加截取图像函数

#### Version 1 rev.B+ (2018.11.29)

1. 删除旧版本冗余JavaDoc，并新增了一些（冗余的JavaDoc）重要使用提示
2. 在  更  多  代  码  之  间  加  了  空  格
3. 调整了一些函数及参量的名称以符合使用者习惯

#### Version 1 rev.B (2018.11.27)
1. 添加四倍分辨率绘制支持API
2. 添加离散化坐标API
3. 修复BUG

#### Version 1 rev.A (2018.11.23)
1. 修改为动态类型，允许实例化
2. 剥离Event,Swing,双缓冲部分
3. 修改部分函数和启动流程，添加基础函数

# OKX Java SDK

这是一个用于访问OKX V5 API的Java SDK，提供了简单易用的接口来与OKX交易所进行交互。

## 功能特点

- 支持OKX V5 API的所有主要功能
- 使用简单，接口清晰
- 完整的Java文档注释
- 包含示例代码

## 快速开始

### 添加依赖

将以下依赖添加到您的`pom.xml`文件中：

```xml
<dependency>
    <groupId>com.okx</groupId>
    <artifactId>okx-sdk</artifactId>
    <version>1.0.0</version>
</dependency>
```

### 基本用法

以下是一个简单的示例，展示如何使用SDK获取市场数据：

```java
// 创建市场数据服务实例
String apiKey = "YOUR-API-KEY";
String secretKey = "YOUR-SECRET-KEY";
String passphrase = "YOUR-PASSPHRASE";

MarketDataService marketDataService = new MarketDataServiceImpl(apiKey, secretKey, passphrase, OkxConfig.BASE_URL);

// 获取BTC-USDT的行情信息
OkxResponse<List<Ticker>> response = marketDataService.getTicker("BTC-USDT");
if (response.isSuccessful()) {
    Ticker ticker = response.getData().get(0);
    System.out.println("BTC-USDT 最新价格: " + ticker.getLast());
}
```

## 功能模块

### 市场数据
- 获取单个产品行情信息
- 获取所有产品行情信息
- 获取K线数据
- 获取深度数据

### 待实现功能
- 交易功能
- 账户功能
- 资金功能
- 其他高级功能

## 注意事项

1. 在使用SDK之前，请确保您已经在OKX平台注册并获取了API密钥。
2. 请妥善保管您的API密钥和密码，不要泄露给他人。
3. 建议在正式环境使用之前，先在测试环境中进行测试。

## 贡献

欢迎提交问题和改进建议！如果您想贡献代码，请：

1. Fork 本仓库
2. 创建您的特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交您的改动 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 开启一个Pull Request

## 许可证

本项目采用 MIT 许可证 - 详见 [LICENSE](LICENSE) 文件

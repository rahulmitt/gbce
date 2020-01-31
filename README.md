# gbce

GBCE is a spring-boot application that let's the users communicate using REST endpoints. We have chosen REST based interface to be futuristic and enable easy integration with other systems later on. This implementation has a 63% unit test coverage for methods.

Assumptions:
1. This application will not create actual trades; just record incoming trades with timestamp, quantity, buy/sell indicator and price.

2. Since, there is a requirement to capture the trades with buy/sell indicator, it is assumed that both long and short side of trades will be present in the system.

3. In order to compute Volume weighted stock price, it will consider only buy side (longs) of trades.

4. Any trades that are for securites not listed in the exchange will be rejected.

5. If values like last-dividend, fixed-dividend or par-value is not present, they are assumed to be 0.

6. The sample data (Reference Data) will be loaded in memory during the bootup time. Although there are APIs to add/remove stocks into/from the exchange, the REST endpoints are not exposed just yet.

```Below are some sample REST endpoints:
http://localhost:8080/stock-exchange/stock/list
http://localhost:8080/stock-exchange/stock/POP/list

http://localhost:8080/stock-exchange/stock/yield/POP/30
http://localhost:8080/stock-exchange/stock/pe/GIN/1

http://localhost:8080/stock-exchange/trade/create/ticker/POP/type/BUY/quantity/100/price/350
http://localhost:8080/stock-exchange/trade/get/POP
http://localhost:8080/stock-exchange/trade/get

http://localhost:8080/stock-exchange/trade/vwap/POP
http://localhost:8080/stock-exchange/trade/index
```

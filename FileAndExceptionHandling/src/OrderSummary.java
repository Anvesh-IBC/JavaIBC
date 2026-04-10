import java.io.*;
import java.util.*;

class OrderSummary implements Serializable {
    private static final long serialVersionUID = 1L;
    int totalOrders;
    int totalQty;
    Map<String, Integer> itemCount = new LinkedHashMap<>();

    @Override
    public String toString() {
        return "OrderSummary{totalOrders=" + totalOrders + ", totalQty=" + totalQty + ", itemCount=" + itemCount + "}";
    }
}


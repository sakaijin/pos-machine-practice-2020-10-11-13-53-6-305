package pos.machine;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PosMachine {

    private String receipt = "***<store earning no money>Receipt***" + "\r\n";
    public String printReceipt(List<String> barcodes) {
        int totalPrice = 0;
        List<String> barcodeSet = barcodes.stream().distinct().collect(Collectors.toList());
        for(String barcode: barcodeSet){
            int timesCalled = timesCalled(barcodes, barcode);
            List<ItemInfo> details = getItem(barcode);
            totalPrice += calculateSubtotal(timesCalled, details.get(0).getPrice());
            receipt += "Name: " + details.get(0).getName() + ", " + "Quantity: " + timesCalled + ", " + "Unit price: " +
                    details.get(0).getPrice() + " (yuan), " + "Subtotal: " + timesCalled*details.get(0).getPrice() + " (yuan)"
                    + "\r\n";
        }
        return receipt + "----------------------" + "\r\n" + "Total: " + totalPrice + " (yuan)" +
                "\r\n" + "**********************";
    }

    private int calculateSubtotal(int timesCalled, int unitPrices) {
        return timesCalled * unitPrices;
    }

    private int timesCalled(List<String> barcodes, String barcode) {
        return Collections.frequency(barcodes, barcode);
    }

    private List<ItemInfo> getItem(String barcode) {
        Predicate<ItemInfo> byBarcode = x -> x.getBarcode().equals(barcode);
        return ItemDataLoader.loadAllItemInfos().stream().filter(byBarcode).collect(Collectors.toList());
    }
}

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ZChartReader {
    private final List<Double> zValues=new ArrayList<>();
    private final List<Double> fValues=new ArrayList<>();
    private final List<Double> lValues=new ArrayList<>();

    public ZChartReader(String filePath){
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split("\\s+");
                Double z = Double.parseDouble(tokens[0]);
                Double f = Double.parseDouble(tokens[1]);
                Double l = Double.parseDouble(tokens[2]);
                zValues.add(z);
                fValues.add(f);
                lValues.add(l);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Double getZValueFromF(Double inputValue){
        double minDifference=Math.abs(inputValue-fValues.getFirst());
        double difference;
        int index = 0;
        for (int i=0;i<fValues.size();i++){
            difference=Math.abs(inputValue-fValues.get(i));
            if(difference<minDifference){
                minDifference=difference;
                index=i;
            }
        }
        return zValues.get(index);
    }

    public Double getLValueFromF(Double inputValue){
        double minDifference=Math.abs(inputValue-fValues.getFirst());
        double difference;
        int index = 0;
        for (int i=0;i<fValues.size();i++){
            difference=Math.abs(inputValue-fValues.get(i));
            if(difference<minDifference){
                minDifference=difference;
                index=i;
            }
        }
        return lValues.get(index);
    }
    public Double getFValue(Double inputValue){
        double minDifference=Math.abs(inputValue-fValues.getFirst());
        double difference;
        int index = 0;
        for (int i=0;i<fValues.size();i++){
            difference=Math.abs(inputValue-fValues.get(i));
            if(difference<minDifference){
                minDifference=difference;
                index=i;
            }
        }
        return fValues.get(index);
    }
}

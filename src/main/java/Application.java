import java.util.Scanner;

public class Application {
    //INITIALIZING Z CHART READER CLASS
    static ZChartReader reader=new ZChartReader("z-chart.txt");

    //CALCULATING HOLDING COST AND ANNUAL DEMAND
    public static float CalculateHoldingCost(float UnitCost, float InterestRate){
        return (float) UnitCost*InterestRate;
    }
    public static float CalculateAnnualDemand(float LeadTime,float LeadTimeDemand){
        return (LeadTimeDemand*(12/LeadTime));
    }

    public static void main(String[] args) {
        //USER INPUT FOR VARIABLES
        Scanner scanner =new Scanner(System.in);

        System.out.println("Enter Unit Cost: ");
        float UnitCost = scanner.nextFloat();
        System.out.println("Enter Ordering Cost: ");
        float OrderingCost = scanner.nextFloat();
        System.out.println("Enter Penalty Cost: ");
        float PenaltyCost = scanner.nextFloat();
        System.out.println("Enter Annual Interest Rate (use , for separation): ");
        float InterestRate = scanner.nextFloat();
        System.out.println("Enter Lead Time in months: ");
        float LeadTime = scanner.nextFloat();
        System.out.println("Enter Lead Time Demand: ");
        float LeadTimeDemand = scanner.nextFloat();
        System.out.println("Enter Lead Time Standard Deviation: ");
        float LeadTimeStandardDeviation = scanner.nextFloat();

        float HoldingCost =CalculateHoldingCost(UnitCost,InterestRate);
        float AnnualDemand = CalculateAnnualDemand(LeadTime,LeadTimeDemand);

        //HARDCODED THE FIRST ITERATION FOR Q0 VALUE
        double Q0= Math.sqrt((2*OrderingCost*(12/LeadTime*LeadTimeDemand))/HoldingCost);
        double FR0=1-Q0*HoldingCost/(PenaltyCost*(12/LeadTime*LeadTimeDemand));
        double zValue=reader.getZValueFromF(FR0);
        double R0=zValue*LeadTimeStandardDeviation+LeadTimeDemand;
        double nR0= reader.getLValueFromF(FR0)*LeadTimeStandardDeviation;

        int counter=1;

        double Q=0;
        double FR=0;
        double zedValue;
        double R=0;
        double nR=nR0;
        double Qex=Q0;
        //ITERATION FOR OPTIMAL LOT SIZE AND REORDER POINT
        while (Q!=Qex){
            Qex=Q;
            Q=Math.sqrt(2*((12/LeadTime*LeadTimeDemand)*(OrderingCost+PenaltyCost*nR)/HoldingCost));
            FR=1-Q*HoldingCost/(PenaltyCost*(12/LeadTime*LeadTimeDemand));
            zedValue=reader.getZValueFromF(FR);
            R=zedValue*LeadTimeStandardDeviation+LeadTimeDemand;
            nR=reader.getLValueFromF(FR)*LeadTimeStandardDeviation;
            counter++;
        }
        System.out.println("Optimal Lot Size: "+Q);
        System.out.println("Reorder Point: "+R);
        System.out.println("Total Iteration: "+counter);
        System.out.println("Safety Stock: " + (R-LeadTimeDemand));
        System.out.println("Annual Holding Cost: "+ (HoldingCost*(Q/2+R-LeadTimeDemand)));
        System.out.println("Annual Setup Cost: "+(OrderingCost*AnnualDemand/Q));
        System.out.println("Annual Penalty Cost: "+ ((PenaltyCost*AnnualDemand*nR)/Q));
        System.out.println("average Time between the placement of orders:"+ (Q/AnnualDemand)+" year");
        System.out.println("Number of Orders:"+(AnnualDemand/Q));
        System.out.println("The proportion of order cycles which no stock out occurs: %"+(reader.getFValue(FR)*100));
        System.out.println("The proportion of demand that are not met: %"+ (100*nR/Q));
    }



}

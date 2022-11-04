//package com.training.repository;
//
//import com.training.model.Cow;
//import com.training.model.Milk;
//
//import java.util.HashMap;
//import java.util.Map;
//
////@JsonPropertyOrder({"ht1","numberOfCows"})
//
//public class Cows {
//
//    private static int totalMilk=0;
//
//    private static int totalProfit=0;
//
//    private int numberOfCows = 0;
//
//    public int getNumberOfCows()
//    {
//        return numberOfCows;
//    }
//
//    public static int getTotalMilk() {
//        return totalMilk;
//    }
//
//    public static void setTotalMilk(int totalMilk) {
//        Cows.totalMilk = totalMilk;
//    }
//
//    public static int getTotalProfit() {
//        return totalProfit;
//    }
//
//    public static void setTotalProfit()
//    {
//        Cows.totalProfit=0;
//        Cows.totalProfit+= Milk.getPurchasedMilkPrice();
//        //System.out.println(Cows.totalProfit);
//        for(Map.Entry<Integer,Cow> e:Cows.ht1.entrySet())
//        {
//            if(e.getValue().getSold().equals("true"))
//            {
//                Cows.totalProfit+=e.getValue().getCost();
//            }
//        }
//    }
//
//    //HashMap declaration
//    static HashMap<Integer, Cow> ht1 = new HashMap<>();
//
//    public HashMap<Integer, Cow> getHt1() {
//        return ht1;
//    }
//
//    public boolean checkPost(Cow cow)
//    {
//        if(!ht1.containsKey(cow.getId()))
//        {
//            add(cow);
//            return true;
//        }
//        return false;
//    }
//
//    public void add (Cow cow){
//        if(cow.getMilking().equals("false"))
//        {
//            cow.setMilkProducedDaily(0);
//            cow.setMilkProducedMonthly(0);
//        }
//        if(cow.getGender().equals("male"))
//        {
//            cow.setMilking("false");
//            cow.setMilkProducedDaily(0);
//            cow.setMilkProducedMonthly(0);
//        }
//        ht1.put(cow.getId(),cow);
//        setTotalMilkDefault();
//        numberOfCows = ht1.size();
//    }
//
//    public boolean checkPatch(int id,Cow cow)
//    {
//        if(ht1.containsKey(id))
//        {
//            update(id,cow);
//            return true;
//        }
//        return false;
//    }
//
//    public void update(int id,Cow cow)
//    {
//        Cow animal=ht1.get(id);
//        //System.out.println(animal.getId());
//        //System.out.println(cow.getSold()+":"+cow.getMilking());
//        if(cow.getId()!=0)
//        {
//            animal.setId(cow.getId());
//        }
//        if(cow.getAge()!= 0)
//        {
//            animal.setAge(cow.getAge());
//        }
//        if(cow.getColor()!=null)
//        {
//            animal.setColor(cow.getColor());
//        }
//        if(cow.getGender()!=null)
//        {
//            animal.setGender(cow.getGender());
//        }
//        if(cow.getOrigin()!=null)
//        {
//            animal.setOrigin(cow.getOrigin());
//        }
//        if(cow.getSold()!=null)
//        {
//            //System.out.println(cow.getSold());
//            animal.setSold(cow.getSold());
//        }
//        if(cow.getCost()!=0)
//        {
//            animal.setCost(cow.getCost());
//        }
//        if(cow.getMilking()!=null)
//        {
//            //System.out.println(cow.getMilking());
//            animal.setMilking(cow.getMilking());
//        }
//        if(cow.getMilkProducedDaily()!=0)
//        {
//            animal.setMilkProducedDaily(cow.getMilkProducedDaily());
//        }
//        if(cow.getMilkProducedMonthly()!=0)
//        {
//            animal.setMilkProducedMonthly(cow.getMilkProducedMonthly());
//        }
//
//        if(animal.getMilking().equals("false"))
//        {
//            animal.setMilkProducedDaily(0);
//            animal.setMilkProducedMonthly(0);
//        }
//        if(animal.getGender().equals("male"))
//        {
//            animal.setMilking("false");
//            animal.setMilkProducedDaily(0);
//            animal.setMilkProducedMonthly(0);
//        }
//        ht1.put(id,animal);
//    }
//
//    public String delete(int id)
//    {
//        if(ht1.containsKey(id))
//        {
//            ht1.remove(id);
//            return "deleted";
//        }
//        return "data is not present to delete";
//    }
//
//    public String checkMilk(int id)
//    {
//        if(ht1.containsKey(id))
//        {
//            return getMilk(id);
//        }
//        return "false";
//    }
//
//    public String getMilk(int id)
//    {
//        Cow animal=ht1.get(id);
//        String milkProducedDaily="milk produced daily:"+animal.getMilkProducedDaily();
//        String milkProducedMonthly="milk produced monthly:"+animal.getMilkProducedMonthly();
//        return milkProducedDaily+","+milkProducedMonthly;
//    }
//
//    public void setTotalMilkDefault()
//    {
//        Cows.totalMilk=0;
//        for(Map.Entry<Integer,Cow> e: ht1.entrySet())
//        {
//            Cows.totalMilk+=e.getValue().getMilkProducedDaily();
//        }
//        //System.out.println("totalMilk= "+totalMilk);
//    }
//}

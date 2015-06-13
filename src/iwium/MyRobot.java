package iwium;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import robocode.AdvancedRobot;

/**
 * Created by mateu_000 on 2015-06-13.
 */
public class MyRobot extends AdvancedRobot{

    private static final String RULES_FILE = "iwium/rules/rules.drl";

    private KnowledgeBuilder kBuilder;
    private KnowledgeBase kBase;

    private void createKnowledgeBase()
    {
        this.kBuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        this.kBuilder.add(ResourceFactory.newClassPathResource(RULES_FILE), ResourceType.DRL);

        if (kBuilder.hasErrors())
        {
            System.err.println(kBuilder.getErrors().toString());
        }

        kBase = KnowledgeBaseFactory.newKnowledgeBase();
        kBase.addKnowledgePackages(kBuilder.getKnowledgePackages());
    }

    public void run(){
        createKnowledgeBase();

        this.setAdjustGunForRobotTurn(true);
        this.setAdjustRadarForGunTurn(true);
        this.setAdjustRadarForRobotTurn(true);

        while(true) {

            StatefulKnowledgeSession kSession = kBase.newStatefulKnowledgeSession();

            kSession.insert(this);

            kSession.fireAllRules();
            kSession.dispose();
        }
    }

    public static void main(String [] args){
        MyRobot r = new MyRobot();
        r.run();

        System.out.print("end");
    }
}

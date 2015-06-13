package iwium;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import robocode.AdvancedRobot;
import robocode.*;

/**
 * Created by mateu_000 on 2015-06-13.
 */
public class MyRobot extends AdvancedRobot{

    private static final String RULES_FILE = "iwium/rules/rules.drl";

    private KnowledgeBuilder kBuilder;
    private KnowledgeBase kBase;
    private StatefulKnowledgeSession kSession;

    int scannedX = Integer.MIN_VALUE;
    int scannedY = Integer.MIN_VALUE;

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

            kSession = kBase.newStatefulKnowledgeSession();

            kSession.insert(this);

            kSession.fireAllRules();
            kSession.dispose();
        }
    }

    @Override
    public void onBulletHit(BulletHitEvent event)
    {
        kSession.insert(event);
    }

    @Override
    public void onBulletHitBullet(BulletHitBulletEvent event)
    {
        kSession.insert(event);
    }

    @Override
    public void onBulletMissed(BulletMissedEvent event)
    {
        kSession.insert(event);
    }

    @Override
    public void onHitByBullet(HitByBulletEvent event)
    {
        kSession.insert(event);
    }

    @Override
    public void onHitRobot(HitRobotEvent event)
    {
        kSession.insert(event);
    }

    @Override
    public void onHitWall(HitWallEvent event)
    {
        kSession.insert(event);
    }

    @Override
    public void onRobotDeath(RobotDeathEvent event)
    {
        kSession.insert(event);
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent e)
    {
        kSession.insert(e);

        double angle = Math.toRadians((getHeading() + e.getBearing()) % 360);
        scannedX = (int) (getX() + Math.sin(angle) * e.getDistance());
        scannedY = (int) (getY() + Math.cos(angle) * e.getDistance());
    }
}

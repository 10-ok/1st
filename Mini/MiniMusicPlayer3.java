package Mini;

import javax.sound.midi.*;
import javax.swing.*;
import java.awt.*;

public class MiniMusicPlayer3 {
    static JFrame f = new JFrame("Мой первый музыкальный клип");
    static MyDrawPanel ml;

    public static  void main(String[] args) {
        MiniMusicPlayer3 Player1 = new MiniMusicPlayer3();
        Player1.go();
    }//end of method main

    public void setUpGui(){
        ml = new MyDrawPanel();
        f.setContentPane(ml);
        f.setBounds(30, 30, 300, 300);
        f.setVisible(true);
    }//end of method setUpGui

    public void go() {
        setUpGui();

        try {
            Sequencer sequencer = MidiSystem.getSequencer(); //creating synthesizer
            sequencer.open();                               //open synthesizer
            sequencer.addControllerEventListener(ml, new int[] {127});
            Sequence seq = new Sequence(Sequence.PPQ, 4);
            Track track = seq.createTrack();

            int r;
            for (int i = 5; i < 61; i += 4) {
                r = (int) ((Math.random() * 50) + 1);
                track.add(makeEvent(144, 1, r, 100, i));
                track.add(makeEvent(176, 1, 127, 0, i));// add Control Event
                track.add(makeEvent(128, 1, r, 100, i + 2));
            }//end of for

            sequencer.setSequence(seq);
            sequencer.start();
            sequencer.setTempoInBPM(120);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }// end of method go


    public static MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {
        MidiEvent event = null;
        try {
            ShortMessage a = new ShortMessage();
            a.setMessage(comd, chan, one, two);
            event = new MidiEvent(a, tick);
        } catch (Exception e) { }
        return event;
    }//end of method makeEvent

    class  MyDrawPanel extends JPanel implements ControllerEventListener {
        boolean msg = false;

        public void controlChange(ShortMessage event){
            msg = true;
            repaint();
        }
        @Override
        public void paintComponent(Graphics g){
            if (msg) {
                Graphics2D g2 = (Graphics2D) g;

                int r = (int) (Math.random() * 250);
                int gr = (int) (Math.random() * 250);
                int b = (int) (Math.random() * 250);

                g.setColor(new Color(r, gr, b));

                int ht = (int) ((Math.random() * 120) + 10);
                int width = (int) ((Math.random() * 120) + 10);

                int x = (int) ((Math.random() * 40) + 10);
                int y = (int) ((Math.random() * 40) + 10);

                g.fillRect(x, y, ht, width);
                msg = false;
            }//end of if
        }//end of method paintComponent
    }//end of inner class MyDrawPanel
}//end of classMiniMusicPlayer1
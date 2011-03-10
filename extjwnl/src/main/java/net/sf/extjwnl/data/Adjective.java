package net.sf.extjwnl.data;

import net.sf.extjwnl.JWNL;
import net.sf.extjwnl.dictionary.Dictionary;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * An <code>Adjective</code> is a <code>Word</code> that can have an adjective position.
 * <p/>
 * Note: Adjective positions are only supported through WordNet v1.5.
 *
 * @author John Didion <jdidion@didion.net>
 * @author Aliaksandr Autayeu <avtaev@gmail.com>
 */
public class Adjective extends Word {

    static {
        JWNL.initialize();
    }

    private static final long serialVersionUID = 1L;

    private static final String NONE_KEY = "none";
    private static final String AP_PREDICATIVE_KEY = "p";
    private static final String AP_ATTRIBUTIVE_KEY = "a";
    private static final String AP_IMMEDIATE_POSTNOMINAL_KEY = "ip";

    private static final Map<String, AdjectivePosition> KEY_TO_OBJECT_MAP = new HashMap<String, AdjectivePosition>();

    public static final AdjectivePosition NONE = new AdjectivePosition("NONE", NONE_KEY);
    public static final AdjectivePosition PREDICATIVE = new AdjectivePosition("AP_PREDICATIVE", AP_PREDICATIVE_KEY);
    public static final AdjectivePosition ATTRIBUTIVE = new AdjectivePosition("AP_ATTRIBUTIVE", AP_ATTRIBUTIVE_KEY);
    public static final AdjectivePosition IMMEDIATE_POSTNOMINAL = new AdjectivePosition("AP_IMMEDIATE_POSTNOMINAL", AP_IMMEDIATE_POSTNOMINAL_KEY);

    public static final AdjectivePosition[] ADJECTIVE_POSITIONS = {NONE, PREDICATIVE, ATTRIBUTIVE, IMMEDIATE_POSTNOMINAL};

    public static AdjectivePosition getAdjectivePositionForKey(String key) {
        return KEY_TO_OBJECT_MAP.get(key);
    }

    private AdjectivePosition adjectivePosition;

    public Adjective(Dictionary dictionary, Synset synset, int index, String lemma, AdjectivePosition adjectivePosition) {
        super(dictionary, synset, index, lemma);
        this.adjectivePosition = adjectivePosition;
    }

    public AdjectivePosition getAdjectivePosition() {
        return adjectivePosition;
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        adjectivePosition = getAdjectivePositionForKey(adjectivePosition.getKey());
    }

    /**
     * Adjective positions denote a restriction on the on the syntactic position the
     * adjective may have in relation to noun that it modifies. Adjective positions are
     * only used through WordNet version 1.6.
     */
    public static final class AdjectivePosition implements Serializable {
        private String key;
        private transient String label;

        private AdjectivePosition(String label, String key) {
            this.label = JWNL.resolveMessage(label);
            this.key = key;
            KEY_TO_OBJECT_MAP.put(key, this);
        }

        public String getKey() {
            return key;
        }

        public String getLabel() {
            return label;
        }

        public String toString() {
            return JWNL.resolveMessage("DATA_TOSTRING_006", label);
        }
    }
}
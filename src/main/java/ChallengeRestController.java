import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jing on 6/15/17.
 */
//        Void Database.insertCard( JSON json )
//        Void Database.modifyCard( String ID, JSON json )
//        Void Database.deleteCard( String ID )
//        JSON Database.getCard( String ID )
//        JSON Conversion.V1toV2( JSON json );
//        JSON Conversion.V2toV1( JSON json );
@RestController
public class ChallengeRestController {

    public ResponseEntity<JSON> createCard(JSON json){
        Database.insertCard(json);
        return new ResponseEntity<JSON>(HttpStatus.CREATED);
    }

    public ResponseEntity<JSON> updateCard(String id, JSON json){
        JSON cur = Database.getCard(id);
        if(cur == null){
            return new ResponseEntity<JSON>(HttpStatus.NOT_FOUND);
        }
        Database.modifyCard(id, json);
        return new ResponseEntity<JSON>(cur, HttpStatus.OK);
    }

    public ResponseEntity<JSON> delete(String id){
        JSON cur = Database.getCard(id);
        if(cur == null){
            return new ResponseEntity<JSON>(HttpStatus.NOT_FOUND);
        }
        Database.deleteCard(id);
        return new ResponseEntity<JSON>(HttpStatus.OK);
    }


    public ResponseEntity<V1> getV1Card (String id) {
        //Can add a log here, something like:
        // logger.info("getting card with id: ", id);
        JSON v = Database.getCard(id);
        if (v == null) {
            return new ResponseEntity<V1>(HttpStatus.NOT_FOUND);
        }
        if (!isV1(v)) {
            v = Conversion.V2toV1(v);
        }
        return new ResponseEntity<V1>(HttpStatus.OK);
    }

    public ResponseEntity<V2> getV2Card (String id) {
        JSON v = Database.getCard(id);
        if (v == null) {
            return new ResponseEntity<V2>(HttpStatus.NOT_FOUND);
        }
        if (isV1(v)) {
            v = Conversion.V1toV2(v);
        }
        return new ResponseEntity<V2>(HttpStatus.OK);
    }

    private boolean isV1(JSON v){
        Object cur = v.get("orange");
        if (cur instanceof JSON){
            return false;
        }
        return true;
    }
}


package controllers;

import javax.inject.*;
import com.fasterxml.jackson.databind.JsonNode;
import play.mvc.*;
import play.libs.Json;
import play.data.DynamicForm;
import play.data.FormFactory;
import models.Segmento;
import models.DBController;
/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    /* An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        DBController.Verify();
        return ok(Segmento.getJSON().toString())
        .as("application/json")
        .withHeader("Access-Control-Allow-Origin"," * ")
        .withHeader("Access-Control-Allow-Methods"," POST, PUT, GET, OPTIONS")
        .withHeader("Access-Control-Allow-Headers"," Origin, X-Requested-With, Content-Type, Accept, Authorization");
    }
    public Result getSegmentoById(int ID){
        return ok(Segmento.getById(ID).toString())
        .as("application/json")
        .withHeader("Access-Control-Allow-Origin"," * ")
        .withHeader("Access-Control-Allow-Methods"," POST, PUT, GET, OPTIONS")
        .withHeader("Access-Control-Allow-Headers"," Origin, X-Requested-With, Content-Type, Accept, Authorization");
    
    }
    @Inject FormFactory formFactory;
    public Result newSegmento(Http.Request request){
        DynamicForm df = formFactory.form().bindFromRequest(request);
        JsonNode jn = Json.parse(df.rawData().toString().replace("{{","{").replace("}}","}").replace("=}",""));
        Segmento.Crear(jn);
                return ok()
        .as("application/json")
        .withHeader("Access-Control-Allow-Origin"," * ")
        .withHeader("Access-Control-Allow-Methods"," POST, PUT, GET, OPTIONS")
        .withHeader("Access-Control-Allow-Headers"," Origin, X-Requested-With, Content-Type, Accept, Authorization")
        .withHeader("Access-Control-Allow-Credentials","true");
        
    }
    public Result updateSegmento(Http.Request request){
        DynamicForm df = formFactory.form().bindFromRequest(request);
        JsonNode jn = Json.parse(df.rawData().toString().replace("{{","{").replace("}}","}").replace("=}",""));
        Segmento.updateById(jn);
        return ok()
        .as("application/json")
        .withHeader("Access-Control-Allow-Origin"," * ")
        .withHeader("Access-Control-Allow-Methods"," POST, PUT, GET, OPTIONS")
        .withHeader("Access-Control-Allow-Headers"," Origin, X-Requested-With, Content-Type, Accept, Authorization")
        .withHeader("Access-Control-Allow-Credentials","true");
        
    }
    public Result delSegmento(int ID){
        Segmento.Eliminar(ID);
        return ok();
    }
    public Result Estados(){
        return ok(DBController.getEstados().toString())
        .as("application/json").withHeader("Access-Control-Allow-Origin"," * ")
        .withHeader("Access-Control-Allow-Methods"," POST, PUT, GET, OPTIONS")
        .withHeader("Access-Control-Allow-Headers"," Origin, X-Requested-With, Content-Type, Accept, Authorization");
    }
    public Result Funcionalidades(){
        return ok(DBController.getFuncionalidades().toString())
        .as("application/json").withHeader("Access-Control-Allow-Origin"," * ")
        .withHeader("Access-Control-Allow-Methods"," POST, PUT, GET, OPTIONS")
        .withHeader("Access-Control-Allow-Headers"," Origin, X-Requested-With, Content-Type, Accept, Authorization");
    }
    public Result Superficies(){
        return ok(DBController.getSuperficies().toString())
        .as("application/json").withHeader("Access-Control-Allow-Origin"," * ")
        .withHeader("Access-Control-Allow-Methods"," POST, PUT, GET, OPTIONS")
        .withHeader("Access-Control-Allow-Headers"," Origin, X-Requested-With, Content-Type, Accept, Authorization");
    }

    public Result TIPOS(){
        return ok(Segmento.getTipo_via().toString()).as("application/json").withHeader("Access-Control-Allow-Origin"," * ")
        .withHeader("Access-Control-Allow-Methods"," POST, PUT, GET, OPTIONS")
        .withHeader("Access-Control-Allow-Headers"," Origin, X-Requested-With, Content-Type, Accept, Authorization");
    }

}

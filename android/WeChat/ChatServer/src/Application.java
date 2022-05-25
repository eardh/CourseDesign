
import com.eardh.annotation.EarController;
import com.eardh.core.TcpServer;
import com.eardh.dispatcher.Dispatcher;
import com.eardh.mapper.UserMapper;
import com.eardh.utils.ConstantPool;
import com.eardh.utils.ScanAnnotation;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Application {
    public static void main(String[] args) throws IOException, SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        new Thread(new TcpServer(9527, new Dispatcher())).start();
    }
}

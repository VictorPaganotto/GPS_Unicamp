package com.example.tcc2;
/** Bibliotecas necessárias para implementação de todas as funcionalidades do programa **/
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.content.Intent;
import android.os.Bundle;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


/** Atividade principal do programa, nessa atividade é realizada:
 * 1) Configuração do pedido de permissão do aplicativo para utilizar a localização do dispositivo
 * 2) Atualização da localização do dispositivo
 * 3) Chamada da tela que mostra a localização do usuário
 */

public class MainActivity extends AppCompatActivity  {
    /** Criação da variável que representa o botão "Localização" **/
    Button GPS;
    /** Criação da variável utilizada para que a atualização da localização do dispostivo somente ocorra quando o botão "Localização" for pressionado  **/
    int clicou;

    /** Função que cria a tela inicial do programa e chama o layout da atividade principal ("R.layout.activity_main") **/
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /** A variável GPS é relacionada com a variável "Localização" que representa o botão feito no layout da tela inicial **/
        GPS = findViewById(R.id.Localizacao);
        /** Dentro dessa função é inserido as ações que devem ocorrer quando esse botão GPS for pressionado pelo usuário **/
        GPS.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                /** No momento que o usuário pressionar o botão "Localização" da tela inicial a variável "clicou" é setada
                 * e a função "permissao_localizacao" é chamada  **/
                clicou=1;
                permissao_localizacao();

            }
        });
    }

    /** Nessa função é configurada a atividade de pedir a permissão do acesso da localizção do dispotivo pelo programa**/
    private void permissao_localizacao() {
        /** O primeiro passo é verificar se o programa ja tem a permissão para acessar a localização do dispositivo, caso essa permissão
         * ja tenha sido concedido é chamada a função "configuracao". Caso a permissão ainda não foi concedida é feito o pedido **/
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        else
            configuracao();
    }

    /** Essa função é nativa do Android, ela envia ao usuário uma solicitação para utilização da localização do dispositivo e informa qual app que esta
     * realizando essa solicitação. Dentro dessa função é captada a resposta do usuário.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                /** Caso o usuário aceite a solicitação a função "configuracao" é chamada **/
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    configuracao();
                } else {
                    /** Caso o usuário não aceite a solicitação sera mostrada a seguinte mensagem "Permissão negada, sem essa permissão o programa não ira funcionar" **/
                    Toast.makeText(this, "Permissão negada, sem essa permissão o programa não ira funcionar", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    public void configuracao(){
        try {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            Intent intent= new Intent(this, MapsActivity.class);
            startActivity(intent);

            LocationListener locationListener = new LocationListener() {
                public void onLocationChanged(Location location) {
                    if (clicou==1) {
                        atualizar(location);
                    }

                }

                public void onStatusChanged(String provider, int status, Bundle extras) { }

                public void onProviderEnabled(String provider) { }

                public void onProviderDisabled(String provider) { }
            };
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }catch(SecurityException ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void atualizar(Location location)
    {
        clicou=0;
        Double latPoint = location.getLatitude();
        Double lngPoint = location.getLongitude();

        Intent intent= new Intent(this, MapsActivity.class);
        intent.putExtra("latitude",latPoint);
        intent.putExtra("longitude",lngPoint);
        startActivity(intent);


    }
}

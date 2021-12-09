APP_NAME="enquiry-pnr"
APP_VERSION="2.1-SNAPSHOT"
JAVA_PARAM="-Xmx101m"
APP_PARAMS=""

BIN_PATH=$TWM_HOME_PARENT/PNR_Enquiry_IndianRailways_v2.0/bin     #PROM-HOME-PARENT :: exported in .bashrc
JAR_PATH=$BIN_PATH/../target/$APP_NAME-$APP_VERSION.jar

echo "Starting '$APP_NAME' with java param: '$JAVA_PARAM', app params: '$APP_PARAMS' at '$JAR_PATH'"
java $JAVA_PARAM $APP_PARAMS -jar $JAR_PATH

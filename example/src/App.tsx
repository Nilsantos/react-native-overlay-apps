import React, { useState } from 'react';

import { StyleSheet, View, Text, TouchableOpacity } from 'react-native';
import OverlayApps, { IChannelConfig } from 'react-native-overlay-apps';

export default function App() {
  const [isShowing, setIsShowing] = useState(false);

  const startOverlay = async () => {
    const channelConfig: IChannelConfig = {
      id: 'channelId',
      name: 'Channel name',
      description: 'Channel description',
      importance: 3,
      enableVibration: false,
    };

    const notificationConfig = {
      channelId: 'channelId',
      id: 3456,
      title: 'Title',
      text: 'Some text',
      icon: 'ic_icon',
    };

    await OverlayApps.createNotificationChannel(channelConfig);
    await OverlayApps.showOverlay(notificationConfig);
    setIsShowing(true);
  };

  const stopOverlay = async () => {
    await OverlayApps.hideOverlay();
    setIsShowing(false);
  };

  return (
    <View style={styles.container}>
      {isShowing ? (
        <TouchableOpacity onPress={stopOverlay}>
          <Text>Parar overlay</Text>
        </TouchableOpacity>
      ) : (
        <TouchableOpacity onPress={startOverlay}>
          <Text>Iniciar overlay</Text>
        </TouchableOpacity>
      )}

      <Text>Result</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
});

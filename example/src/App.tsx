import React, { useState } from 'react';

import {
  StyleSheet,
  View,
  Text,
  TouchableOpacity,
  TextInput,
} from 'react-native';
import OverlayApps from 'react-native-overlay-apps';

export default function App() {
  const [isShowing, setIsShowing] = useState(false);
  const [text, setText] = useState('');

  const startOverlay = async () => {
    const hasPermission = await OverlayApps.askPermission();
    if (hasPermission) {
      OverlayApps.showOverlay();
      setIsShowing(true);
    }
  };

  const stopOverlay = () => {
    OverlayApps.hideOverlay();
    setIsShowing(false);
  };

  const changeOverlayText = () => {
    OverlayApps.setText(text);
  };

  return (
    <View style={styles.container}>
      {isShowing ? (
        <TouchableOpacity style={styles.button} onPress={stopOverlay}>
          <Text style={styles.text}>Stop overlay</Text>
        </TouchableOpacity>
      ) : (
        <TouchableOpacity style={styles.button} onPress={startOverlay}>
          <Text style={styles.text}>Start overlay</Text>
        </TouchableOpacity>
      )}

      <TextInput
        style={styles.input}
        onChangeText={(t) => setText(t)}
        value={text}
      />

      <TouchableOpacity style={styles.button} onPress={changeOverlayText}>
        <Text style={styles.text}>Change overlay text</Text>
      </TouchableOpacity>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    padding: 40,
  },
  button: {
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: '#006400',
    borderRadius: 10,
    width: '100%',
    height: 40,
  },
  text: {
    color: '#FFFFFF',
  },
  input: {
    marginTop: 50,
    width: '100%',
    height: 40,
    margin: 12,
    borderWidth: 1,
    borderRadius: 10,
    padding: 10,
  },
});

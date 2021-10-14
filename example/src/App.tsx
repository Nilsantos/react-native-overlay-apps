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

  const startOverlay = () => {
    OverlayApps.showOverlay();
    setIsShowing(true);
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
        <TouchableOpacity onPress={stopOverlay}>
          <Text>Parar overlay</Text>
        </TouchableOpacity>
      ) : (
        <TouchableOpacity onPress={startOverlay}>
          <Text>Iniciar overlay</Text>
        </TouchableOpacity>
      )}

      <TextInput
        style={styles.input}
        onChangeText={(t) => setText(t)}
        value={text}
      />

      <TouchableOpacity onPress={changeOverlayText}>
        <Text>Change overlay text</Text>
      </TouchableOpacity>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  input: {
    height: 40,
    margin: 12,
    borderWidth: 1,
    padding: 10,
  },
});

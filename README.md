# react-native-overlay-apps

React native library to writing text over other applications

## Installation

```sh
npm install react-native-overlay-apps
```

## Usage

```js
import OverlayApps from 'react-native-overlay-apps';

const startOverlay = async () => {
  const hasPermission = await OverlayApps.askPermission();
  if (hasPermission) {
    OverlayApps.showOverlay();
    OverlayApps.setText('Hello World');
  }
};

const stopOverlay = () => {
  OverlayApps.hideOverlay();
};
```

A full code example can be found at https://github.com/Nilsantos/react-native-overlay-apps/blob/master/example/src/App.tsx

## Contributing

<table>
  <tr>
    <td align="center"><a href="https://github.com/Nilsantos"><img src="https://avatars.githubusercontent.com/u/44170812?v=4" width="100px;" alt="Nilsantos"/><br><sub><b>Nilsantos</b></sub></a></td>
    <td align="center"><a href="https://github.com/Mdiaas"><img src="https://avatars.githubusercontent.com/u/49025512?v=4" width="100px;" alt="Mdiaas"/><br><sub><b>Mdiaas</b></sub></a></td>
  </tr>
</table>

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT

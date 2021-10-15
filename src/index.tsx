import { NativeModules } from 'react-native';

type OverlayAppsType = {
  showOverlay: () => void;
  hideOverlay: () => void;
  setText: (text: string) => void;
  askPermission: () => Promise<boolean>;
};

const { OverlayApps } = NativeModules;

export default OverlayApps as OverlayAppsType;

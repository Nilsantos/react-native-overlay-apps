import { NativeModules } from 'react-native';

type OverlayAppsType = {
  multiply(a: number, b: number): Promise<number>;
};

const { OverlayApps } = NativeModules;

export default OverlayApps as OverlayAppsType;

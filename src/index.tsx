import { NativeModules } from 'react-native';

export interface IChannelConfig {
  id: string;
  name: string;
  description: string;
  importance: 1 | 2 | 3 | 4 | 5;
  enableVibration: boolean;
}

type OverlayAppsType = {
  createNotificationChannel: (channelConfig: IChannelConfig) => void;
  showOverlay: (notificationConfig: any) => Promise<void>;
  hideOverlay: () => Promise<void>;
};

const { OverlayApps } = NativeModules;

export default OverlayApps as OverlayAppsType;

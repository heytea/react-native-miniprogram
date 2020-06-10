import { NativeModules } from 'react-native';

const { ReactNativeMiniprogram } = NativeModules;

export const MINIPTOGRAM_TYPE = {
    MINIPTOGRAM_TYPE_RELEASE: 0,
    MINIPROGRAM_TYPE_TEST: 1,
    MINIPROGRAM_TYPE_PREVIEW: 2
  }

export default ReactNativeMiniprogram;

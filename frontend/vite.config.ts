import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

export default defineConfig({
    plugins: [react()],
    server: {
        host: '0.0.0.0',
        port: 3000,
        strictPort: true,
        hmr: {
            // Use the hostname your browser will use to reach the container
            host: 'localhost',      // **change this to localhost**
            clientPort: 3000,
            port: 3000
        },
        watch: {
            usePolling: true,       // **enable polling** for reliable watch under Docker/Win
            interval: 500
        },
        cors: true
    }
});

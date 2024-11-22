import SwiftUI
import ComposeApp
import FirebaseCore

@main
struct iOSApp: App {

    init() {
        MainViewControllerKt.doInitKoin()
        FirebaseApp.configure()
    }

	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}

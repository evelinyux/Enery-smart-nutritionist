public final class HomeView extends JPanel
        implements PropertyChangeListener {

    public static final String VIEW_NAME = "home";

    private final LoggedInViewModel loggedInViewModel;
    private final ViewManagerModel viewManagerModel;

    private LogoutController logoutController;
    private ChangePasswordController changePasswordController;

    private final JLabel usernameLabel = new JLabel();

    public HomeView(
            LoggedInViewModel loggedInViewModel,
            ViewManagerModel viewManagerModel) {

        this.loggedInViewModel = loggedInViewModel;
        this.viewManagerModel = viewManagerModel;

        loggedInViewModel.addPropertyChangeListener(this);

        final JButton loggingButton = new JButton("Log Food and Exercise");
        final JButton trackingButton = new JButton("View Progress");
        final JButton profileButton = new JButton("Profile");
        final JButton logoutButton = new JButton("Log Out");

        loggingButton.addActionListener(event -> {
            viewManagerModel.setState(LoggingViewModel.VIEW_NAME);
            viewManagerModel.firePropertyChanged();
        });

        trackingButton.addActionListener(event -> {
            viewManagerModel.setState(TrackingViewModel.VIEW_NAME);
            viewManagerModel.firePropertyChanged();
        });

        profileButton.addActionListener(event -> {
            viewManagerModel.setState(ProfileViewModel.VIEW_NAME);
            viewManagerModel.firePropertyChanged();
        });

        logoutButton.addActionListener(event -> {
            final LoggedInState state = loggedInViewModel.getState();
            logoutController.execute(state.getUsername());
        });

        add(usernameLabel);
        add(profileButton);
        add(loggingButton);
        add(trackingButton);
        add(logoutButton);
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if ("state".equals(event.getPropertyName())) {
            final LoggedInState state =
                    (LoggedInState) event.getNewValue();

            usernameLabel.setText(
                    "Welcome, " + state.getUsername());
        }
    }

    public String getViewName() {
        return VIEW_NAME;
    }

    public void setLogoutController(
            LogoutController logoutController) {
        this.logoutController = logoutController;
    }
}
/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PedidoComponentsPage, PedidoDeleteDialog, PedidoUpdatePage } from './pedido.page-object';

const expect = chai.expect;

describe('Pedido e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let pedidoUpdatePage: PedidoUpdatePage;
    let pedidoComponentsPage: PedidoComponentsPage;
    let pedidoDeleteDialog: PedidoDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Pedidos', async () => {
        await navBarPage.goToEntity('pedido');
        pedidoComponentsPage = new PedidoComponentsPage();
        expect(await pedidoComponentsPage.getTitle()).to.eq('Pedidos');
    });

    it('should load create Pedido page', async () => {
        await pedidoComponentsPage.clickOnCreateButton();
        pedidoUpdatePage = new PedidoUpdatePage();
        expect(await pedidoUpdatePage.getPageTitle()).to.eq('Create or edit a Pedido');
        await pedidoUpdatePage.cancel();
    });

    it('should create and save Pedidos', async () => {
        const nbButtonsBeforeCreate = await pedidoComponentsPage.countDeleteButtons();

        await pedidoComponentsPage.clickOnCreateButton();
        await promise.all([
            pedidoUpdatePage.setNotaInput('nota'),
            pedidoUpdatePage.setGuiaInput('guia'),
            pedidoUpdatePage.estatusSelectLastOption(),
            pedidoUpdatePage.setMetaDataInput('metaData'),
            pedidoUpdatePage.proveedorSelectLastOption()
            // pedidoUpdatePage.productosSelectLastOption(),
        ]);
        expect(await pedidoUpdatePage.getNotaInput()).to.eq('nota');
        expect(await pedidoUpdatePage.getGuiaInput()).to.eq('guia');
        expect(await pedidoUpdatePage.getMetaDataInput()).to.eq('metaData');
        await pedidoUpdatePage.save();
        expect(await pedidoUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await pedidoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Pedido', async () => {
        const nbButtonsBeforeDelete = await pedidoComponentsPage.countDeleteButtons();
        await pedidoComponentsPage.clickOnLastDeleteButton();

        pedidoDeleteDialog = new PedidoDeleteDialog();
        expect(await pedidoDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Pedido?');
        await pedidoDeleteDialog.clickOnConfirmButton();

        expect(await pedidoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
